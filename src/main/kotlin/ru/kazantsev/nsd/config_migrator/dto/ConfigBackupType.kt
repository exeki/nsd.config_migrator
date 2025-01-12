package ru.kazantsev.nsd.config_migrator.dto

enum class ConfigBackupType (
    val code : String
) {
    DURING_MIGRATION_FROM("during_migration_from"),
    DURING_MIGRATION_TO("migrating_migration_to"),
    SCHEDULER("scheduler_from"),
}