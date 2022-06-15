package com.dalo.usermanagement.controller

import com.dalo.usermanagement.dao.UserRepository
import com.dalo.usermanagement.model.User
import com.dalo.usermanagement.testcontainer.LocalhostPostgeSQLContainer
import io.kotest.core.spec.style.BehaviorSpec
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@MicronautTest
class UserCRUDControllerTest(private val userRepository: UserRepository): BehaviorSpec({
    val postgreSQLContainer = LocalhostPostgeSQLContainer.getInstance()

    beforeTest {
        postgreSQLContainer.start()
    }

    afterTest {
        postgreSQLContainer.stop()
    }

    Given("name") {
        withContext(Dispatchers.IO) {
            userRepository.save(User("Daniil", "Shyshla", byteArrayOf()))
        }
        When("name") {
            val user = withContext(Dispatchers.IO) {
                userRepository.findById(1L)
            }
            Then("name") {
                user.isPresent
            }
        }
    }

})