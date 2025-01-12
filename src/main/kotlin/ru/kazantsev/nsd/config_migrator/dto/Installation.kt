package ru.kazantsev.nsd.config_migrator.dto

class Installation (
    val protocol: String,
    val accessKey: String,
    val host: String
) {
    var appVersion: String? = null
    var groovyVersion: String? = null
    var backupConfigWhileMigration = true
}