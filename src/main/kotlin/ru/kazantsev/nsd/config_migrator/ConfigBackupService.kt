package ru.kazantsev.nsd.config_migrator

import ru.kazantsev.nsd.config_migrator.dto.ConfigBackup
import ru.kazantsev.nsd.config_migrator.dto.ConfigBackupType
import ru.kazantsev.nsd.config_migrator.dto.Installation
import java.time.format.DateTimeFormatter

open class ConfigBackupService (
    protected val connectorService: ConnectorService
) {

    var datePattern : String = "dd.MM.yyyy-HH;mm;ss"

    protected val formatter = DateTimeFormatter.ofPattern(datePattern)

    fun fetchAndCreateBackup(inst : Installation, type : ConfigBackupType): ConfigBackup {
        val con = connectorService.getConnectorForInstallation(inst)
        val conf = con.metainfo()
        return ConfigBackup(inst, type, conf)
    }

}