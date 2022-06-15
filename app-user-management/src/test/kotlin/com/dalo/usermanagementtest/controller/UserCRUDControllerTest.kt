package com.dalo.usermanagementtest.controller

import com.dalo.usermanagementtest.dao.UserRepository
import com.dalo.usermanagementtest.model.User
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.optional.shouldBePresent
import io.kotest.matchers.shouldBe
import io.micronaut.http.MediaType
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@MicronautTest
class UserCRUDControllerTest(userRepository: UserRepository, mockMvc: MockMvc): BehaviorSpec({
    val BASE_PATH = "/api/users"

    Given("name") {
        withContext(Dispatchers.IO) {
            userRepository.save(User("Danilo", "Shyshlo", byteArrayOf()))
        }
        When("name") {
            val response = mockMvc.perform(get("$BASE_PATH/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .response
            Then("name") {
                response.status.shouldBe(HttpStatus.OK.value())
            }
        }
    }
})
