package com.dalo.usermanagementtest.config

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
                val ddd = instance.jdbcUrl + "&currentSchema=users"
                System.out.println(ddd)
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