package ru.kazantsev.nsd.config_migrator

import com.fasterxml.jackson.databind.ObjectMapper
import ru.kazantsev.nsd.basic_api_connector.Connector
import ru.kazantsev.nsd.basic_api_connector.ConnectorParams
import ru.kazantsev.nsd.config_migrator.dto.Installation

open class ConnectorService(val objectMapper: ObjectMapper) {

    protected val connectorMap = mutableMapOf<String, Connector>()

    fun getConnectorParamsForInstallation(installation: Installation): ConnectorParams {
        return ConnectorParams(
            installation.host,
            installation.protocol,
            installation.host,
            installation.accessKey,
            true
        )
    }

    fun getConnectorForInstallation(installation: Installation): Connector {
        var con = connectorMap.get(installation.host)
        if (con != null) return con
        else {
            val params = getConnectorParamsForInstallation(installation)
            con = Connector(params)
            con.setObjectMapper(objectMapper)
            connectorMap.put(installation.host, con)
            return con
        }
    }
}