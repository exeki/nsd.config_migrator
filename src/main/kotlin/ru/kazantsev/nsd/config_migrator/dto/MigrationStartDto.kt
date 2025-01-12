package ru.kazantsev.nsd.config_migrator.dto

class MigrationStartDto (
    val migrationLog : MigrationLog,
    val fromConfigBackup: ConfigBackup,
    val toConfigBackup: ConfigBackup,
) {}