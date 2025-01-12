package ru.kazantsev.nsd.config_migrator.dto

enum class MigrationState (val code : String) {
    STARTED ("started"),
    COMPLETED("completed"),
    LOST_PROCESS("lostProcess"),
    ERROR("error");
}