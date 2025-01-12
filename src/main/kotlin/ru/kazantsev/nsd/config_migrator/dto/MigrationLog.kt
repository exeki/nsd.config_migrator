package ru.kazantsev.nsd.config_migrator.dto

import java.time.LocalDateTime

class MigrationLog(
    val from: Installation,
    val to : Installation,
    var overrideAll : Boolean = false,
    var fromBackup : ConfigBackup? = null,
    var toBackup : ConfigBackup? = null
) {
    val startDateTime : LocalDateTime = LocalDateTime.now()
    var currentMigrationState : MigrationState = MigrationState.STARTED
    var errorText : String? = null
}