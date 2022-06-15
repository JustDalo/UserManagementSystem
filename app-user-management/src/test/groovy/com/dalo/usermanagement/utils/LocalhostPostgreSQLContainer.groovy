package com.dalo.usermanagement.testcontainer

import org.testcontainers.containers.PostgreSQLContainer

class LocalhostPostgreSQLContainer extends PostgreSQLContainer<LocalhostPostgreSQLContainer> {
    private static final String IMAGE_VERSION = "postgres:14"
    private static LocalhostPostgreSQLContainer container

    private LocalhostPostgreSQLContainer() {
        super(IMAGE_VERSION)
    }

    static LocalhostPostgreSQLContainer getInstance() {
        if (container == null) {
            container = new LocalhostPostgreSQLContainer()
        }
        return container
    }

    void close() {
        container.close()
    }
}
