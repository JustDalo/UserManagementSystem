package com.dalo.usermanagementtest.config

import org.testcontainers.containers.PostgreSQLContainer

class LocalhostPostgeSQLContainer : PostgreSQLContainer<LocalhostPostgeSQLContainer>("postgres:14") {
    companion object {
        private lateinit var instance: LocalhostPostgeSQLContainer

        fun start() {
            if (!Companion::instance.isInitialized) {
                instance = LocalhostPostgeSQLContainer()
                instance.start()

                System.setProperty("spring.datasource.url=", instance.jdbcUrl + "&currentSchema=users")
                System.setProperty("spring.datasource.username=", instance.username)
                System.setProperty("spring.datasource.password=", instance.password)
            }
        }

        fun stop() {
            instance.stop()
        }
    }
}