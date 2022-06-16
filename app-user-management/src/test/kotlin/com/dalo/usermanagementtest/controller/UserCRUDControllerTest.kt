package com.dalo.usermanagementtest.controller

import com.dalo.usermanagementtest.dao.UserRepository
import com.dalo.usermanagementtest.model.User
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.MediaType
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@MicronautTest
class UserCRUDControllerTest(userRepository: UserRepository, mockMvc: MockMvc) : BehaviorSpec({
    val BASE_PATH = "/api/users"

    Given("user repository for find by id") {
        withContext(Dispatchers.IO) {
            userRepository.save(User("Danilo", "Shyshlo", byteArrayOf()))
        }
        When("requesting user") {
            val response = mockMvc.perform(
                get("$BASE_PATH/1").contentType(MediaType.APPLICATION_JSON)
            )
                .andReturn()
                .response
            Then("findById should return status 200 OK when getting user") {
                response.status.shouldBe(HttpStatus.OK.value())
            }
        }
    }

    Given("empty user repository for find by id that does not exists in databse") {
        When("requesting user") {
            val response = mockMvc.perform(
                get("$BASE_PATH/1").contentType(MediaType.APPLICATION_JSON)
            )
                .andReturn()
                .response
            Then("findById should return status 404 NOT FOUND when getting user that does not exist in database") {
                response.status.shouldBe(HttpStatus.NOT_FOUND.value())
            }
        }
    }

    Given("user repository for find all") {
        withContext(Dispatchers.IO) {
            userRepository.save(User("Danilo", "Shyshlo", byteArrayOf()))
            userRepository.save(User("Daniil", "Shyshlo", byteArrayOf()))
        }
        When("requesting user") {
            val response = mockMvc.perform(
                get(BASE_PATH).contentType(MediaType.APPLICATION_JSON)
            )
                .andReturn()
                .response
            Then("findAll should return status 200 OK when getting all users") {
                response.status.shouldBe(HttpStatus.OK.value())
            }
        }
    }

    Given("user repository for saving user without attached file") {
        val userMultipartFile = MockMultipartFile(
            "user",
            null,
            "text/plain",
            "{\"firstName\": \"Daniil\", \"lastName\": \"Shyshla\"}".toByteArray()
        )
        When("requesting user") {
            val response = mockMvc.perform(
                multipart(BASE_PATH).file(userMultipartFile)
            )
                .andReturn()
                .response
            Then("createUser should return status 201 CREATED when creating new user without attached file") {
                response.status.shouldBe(HttpStatus.CREATED.value())
            }
        }
    }

    Given("user repository for saving user with attached file") {
        val userMultipartFile = MockMultipartFile(
            "user",
            null,
            "text/plain",
            "{\"firstName\": \"Daniil\", \"lastName\": \"Shyshla\"}".toByteArray()
        )
        val fileMultipartFile = MockMultipartFile(
            "file",
            null,
            "application/json",
            "".toByteArray()
        )
        When("requesting user") {
            val response = mockMvc.perform(
                multipart(BASE_PATH).file(userMultipartFile).file(fileMultipartFile)
            )
                .andReturn()
                .response
            Then("createUser should return status 201 CREATED when creating new user with attached file") {
                response.status.shouldBe(HttpStatus.CREATED.value())
            }
        }
    }

    Given("user repository for deleting user") {
        val user = userRepository.save(User("Danilo", "Shyshlo", byteArrayOf()))
        When("deleting user") {
            val response = mockMvc.perform(
                delete(BASE_PATH + "/" + user.getId()).contentType(MediaType.APPLICATION_JSON)
            )
                .andReturn()
                .response
            Then("deleteUser should return status 200 OK when deleting used with given id") {
                response.status.shouldBe(HttpStatus.OK.value())
            }
        }
    }

    Given("empty user repository for deleting user") {
        When("requesting user") {
            val response = mockMvc.perform(
                delete("$BASE_PATH/1").contentType(MediaType.APPLICATION_JSON)
            )
                .andReturn()
                .response
            Then("deleteUser should return status 404 NOT FOUND when used with given id is not found") {
                response.status.shouldBe(HttpStatus.NOT_FOUND.value())
            }
        }
    }

    afterTest {
        withContext(Dispatchers.IO) {
            userRepository.deleteAll()
        }
    }
})
