package com.dalo.usermanagementtest.config

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext

object TestPropertiesInitializer: ApplicationContextInitializer<ConfigurableApplicationContext> {
    private val myPostgreSQLContainer = LocalhostPostgeSQLContainer.getInstance()

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        TestPropertyValues.of(
            "spring.datasource.url=${myPostgreSQLContainer.getJdbcUrl()}",
            "spring.datasource.username=${myPostgreSQLContainer.getUsername()}",
            "spring.datasource.password=${myPostgreSQLContainer.getPassword()}"
        ).applyTo(applicationContext.environment)
    }

}