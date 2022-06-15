package com.dalo.usermanagementtest.spec

import io.kotest.core.Tuple2
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import jakarta.inject.Inject
import org.flywaydb.core.Flyway

abstract class DatabaseBehavioralSpec(body: BehaviorSpec.() -> Unit = {}) : BehaviorSpec(body) {
    lateinit var flyway: Flyway
}