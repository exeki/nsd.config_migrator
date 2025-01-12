package ru.kazantsev.nsd.config_migrator.dto

import java.time.LocalDateTime

class ConfigBackup (
    val installation: Installation,
    val type: ConfigBackupType,
    val config : String
) {
    val date: LocalDateTime = LocalDateTime.now()
}