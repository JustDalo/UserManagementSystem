package com.dalo.usermanagement.testcontainer

import org.testcontainers.containers.PostgreSQLContainer

class LocalhostPostgeSQLContainer : PostgreSQLContainer<LocalhostPostgeSQLContainer>("postgres:14") {
    companion object {
        private lateinit var instance: LocalhostPostgeSQLContainer

        fun getInstance(): LocalhostPostgeSQLContainer {
            return instance
        }

        fun start() {
            if (!Companion::instance.isInitialized) {
                instance = LocalhostPostgeSQLContainer()
                instance.start()

                System.setProperty("datasources.default.url", instance.jdbcUrl)
                System.setProperty("datasources.default.username", instance.username)
                System.setProperty("datasources.default.password", instance.password)
            }
        }

        fun stop() {
            instance.stop()
        }
    }
}