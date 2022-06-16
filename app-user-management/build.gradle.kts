plugins {
    id("org.springframework.boot") version "2.6.7"

    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    //project modules
    implementation(project(":cmn-user"))

    //this module
    implementation(kotlin("stdlib"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation ("org.springframework.boot:spring-boot-starter-security")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.8")

    //database
    runtimeOnly("org.postgresql:postgresql")

    //lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    //mock
    testImplementation("io.mockk:mockk:1.12.4")

    //micronaut
    testImplementation("io.micronaut.test:micronaut-test-kotest:3.4.0")
    testImplementation("io.micronaut.sql:micronaut-jdbc-hikari:4.4.0")
    testImplementation("io.micronaut.flyway:micronaut-flyway:5.3.0")

    //kotest
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.1")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.3.0")
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.3.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test-jvm:1.6.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.6.2")

    //testcontainers
    testImplementation("org.testcontainers:testcontainers:1.17.2")
    testImplementation("org.testcontainers:postgresql:1.17.2")
    testImplementation("io.kotest.extensions:kotest-extensions-testcontainers:1.3.3")

    //spring tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test:5.7.1")
}