package com.dalo.usermanagementtest.controller

import com.dalo.usermanagementtest.dao.UserRepository
import com.dalo.usermanagementtest.model.User
import com.dalo.usermanagementtest.config.LocalhostPostgeSQLContainer
import com.dalo.usermanagementtest.config.TestPropertiesInitializer
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.optional.shouldBePresent
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ContextConfiguration(initializers = [TestPropertiesInitializer])
@MicronautTest
class UserCRUDControllerTest(userRepository: UserRepository): BehaviorSpec({
    Given("name") {
        withContext(Dispatchers.IO) {
            userRepository.save(User("Danilo", "Shyshlo", byteArrayOf()))
        }
        When("name") {
            val user = withContext(Dispatchers.IO) {
                userRepository.findById(1L)
            }
            Then("name") {
                user.shouldBePresent()
            }
        }
    }
})
