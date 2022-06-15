package io.micronaut.test.kotest

import com.dalo.usermanagement.testcontainer.LocalhostPostgeSQLContainer
import io.kotest.core.config.AbstractProjectConfig
import io.micronaut.test.extensions.kotest.MicronautKotestExtension

object ProjectConfig : AbstractProjectConfig() {
    override fun listeners() = listOf(MicronautKotestExtension)
    override fun extensions() = listOf(MicronautKotestExtension)

    override fun beforeAll() {
        LocalhostPostgeSQLContainer.start()
    }

    override fun afterAll() {
        LocalhostPostgeSQLContainer.stop()
    }
}