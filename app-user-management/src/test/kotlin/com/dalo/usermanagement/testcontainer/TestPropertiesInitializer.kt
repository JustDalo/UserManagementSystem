package com.dalo.usermanagement.testcontainer

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.PostgreSQLContainer

class TestPropertiesInitializer: ApplicationContextInitializer<ConfigurableApplicationContext> {
    companion object {
        private val myPostgreSQLContainer = LocalhostPostgeSQLContainer.getInstance()
    }

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        TestPropertyValues.of(
            "spring.datasource.url=${myPostgreSQLContainer.getJdbcUrl()}",
            "spring.datasource.username=${myPostgreSQLContainer.getUsername()}",
            "spring.datasource.password=${myPostgreSQLContainer.getPassword()}"
        ).applyTo(applicationContext.environment)
    }

}