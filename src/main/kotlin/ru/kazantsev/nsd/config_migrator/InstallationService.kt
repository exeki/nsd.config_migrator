package ru.kazantsev.nsd.config_migrator

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.kazantsev.nsd.config_migrator.dto.Installation
import ru.kazantsev.nsd.config_migrator.dto.MigrationLog
import ru.kazantsev.nsd.config_migrator.dto.MigrationStartDto
import java.net.SocketTimeoutException

open class InstallationService(
    protected val connectorService: ConnectorService,
    protected val configBackupService: ConfigBackupService,
) {

    protected val logger: Logger = LoggerFactory.getLogger(InstallationService::class.java)

    fun createInstallationAndFetchData(protocol: String, host: String, accessKey: String): Installation {
        val installation = Installation(protocol, host, accessKey)
        return fetchInstallationData(installation)
    }

    fun fetchInstallationData(inst: Installation): Installation {
        val con = connectorService.getConnectorForInstallation(inst)
        inst.appVersion = con.version()
        inst.groovyVersion = con.groovyVersion()
        return inst
    }


    fun startMigration(
        from: Installation,
        to: Installation,
        overrideAll: Boolean
    ): MigrationStartDto {
        val log = MigrationLog(from, to, overrideAll)
        from.lastFromMigrationLog = log
        to.lastToMigrationLog = log
        try {
            val con = connectorService.getConnectorForInstallation(from)
            val config = con.metainfo()
            if (from.backupConfigWhileMigration) log.fromBackup =
                configBackupService.createBackup(from, config, ConfigBackupType.DURING_MIGRATION_FROM)
            if (to.backupConfigWhileMigration) log.toBackup =
                configBackupService.fetchAndCreateBackup(to, ConfigBackupType.DURING_MIGRATION_TO)
            try {
                con.uploadMetainfo(config, 1000)
            } catch (ignored: SocketTimeoutException) {
                logger.info("Словил SocketTimeoutException при отправке метаинфы. This is fine...")
            }
            log.currentMigrationState = MigrationState.IN_PROGRESS
        } catch (e: Exception) {
            log.currentMigrationState = MigrationState.ERROR
            log.errorText = e.message
        }
        migrationLogRepo.save(log)
        installationRepo.save(from)
        installationRepo.save(to)
        return log
    }

    fun startMigration(migrationPath: MigrationPath): MigrationLog {
        val log = startMigration(migrationPath.from, migrationPath.to, migrationPath.overrideAll)
        migrationPath.lastLog = log
        migrationPathRepo.save(migrationPath)
        return log
    }
}