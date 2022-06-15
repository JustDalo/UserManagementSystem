package com.dalo.usermanagement.controller

import com.dalo.usermanagement.dao.UserRepository
import com.dalo.usermanagement.model.User
import com.dalo.usermanagement.service.UserService
import com.dalo.usermanagement.testcontainer.TestPropertiesInitializer
import com.dalo.usermanagement.testcontainer.LocalhostPostgreSQLContainer
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.http.HttpStatus
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = [TestPropertiesInitializer])
@AutoConfigureMockMvc
class UserCRUDControllerTest extends Specification {
    @Shared
    PostgreSQLContainer postgreSQLContainer = LocalhostPostgreSQLContainer.getInstance()
    @Autowired
    MockMvc mockMvc
    @Autowired
    UserService userService
    @Autowired
    UserRepository userRepository

    private JacksonTester<User> jsonUsers

    private static final String BASE_PATH = "/api/users"

    def setup() {
        JacksonTester.initFields(this, new ObjectMapper())
    }

    def cleanup() {
        userRepository.deleteAll()
    }

    def "findById should return status 200 OK when getting user"() {
        given:
            User savedUser = createNewUser()
        when:
            def response = mockMvc.perform(get(BASE_PATH + "/${savedUser.getId()}")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
        then:
            response.getStatus() == HttpStatus.OK.value()
    }

    def "findById should return status 404 NOT FOUND when getting user that does not exist in database"() {
        when:
            def response = mockMvc.perform(get(BASE_PATH + "/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
        then:
            response.getStatus() == HttpStatus.NOT_FOUND.value()
    }

    def "findAll should return status 200 OK when getting all users"() {
        given:
            userRepository.save(new User(firstName: "Danil",
                lastName: "Shyshla",
                middleName: "Valerevich",
                sex: "male",
                phoneNumber: "+375111111111",
                email: "@gmail.com",
                country: belarusCountry))
            userRepository.save(new User(firstName: "Ivan",
                lastName: "Ivanov",
                middleName: "Ivanovich",
                sex: "male",
                phoneNumber: "+375000000000",
                email: "@gmail.com",
                country: belarusCountry))
        when:
            def response = mockMvc.perform(get(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
        then:
            response.getStatus() == HttpStatus.OK.value()
    }

    def "createUser should return status 201 CREATED when creating new user without attached file"() {
        given:
            def userMultipartFile = new MockMultipartFile("user",
                null,
                "application/json",
                "{\"firstName\": \"Daniil\", \"lastName\": \"Shyshla\", \"sex\": \"male\", \"phoneNumber\": \"+37533\", \"email\": \"email\"}".getBytes())
            def request = Mock(HttpServletRequest)
            ipRequestService.getClientIP(request) >> null
        when:
            def response = mockMvc.perform(multipart(BASE_PATH)
                .file(userMultipartFile))
                .andReturn()
                .getResponse()
        then:
            response.getStatus() == HttpStatus.CREATED.value()
    }

    def "createUser should return status 201 CREATED when creating new user with attached file"() {
        given:
            def request = Mock(HttpServletRequest)
            def userMultipartFile = new MockMultipartFile("user",
                null,
                "application/json",
                "{\"firstName\": \"Daniil\", \"lastName\": \"Shyshla\", \"sex\": \"male\", \"phoneNumber\": \"+37533\", \"email\": \"email\"}".getBytes())
            def imageMultipartFile = new MockMultipartFile(
                "file",
                "image.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "image bytes!".getBytes()
            )
            ipRequestService.getClientIP(request) >> null
        when:
            def response = mockMvc.perform(multipart(BASE_PATH)
                .file(userMultipartFile)
                .file(imageMultipartFile))
                .andReturn()
                .getResponse()
        then:
            response.getStatus() == HttpStatus.CREATED.value()

    }

    def "createUser should return status 400 BAD REQUEST if some of the required properties were missed"() {
        given:
            def request = Mock(HttpServletRequest)
            ipRequestService.getClientIP(request) >> null
        when:
            def response = mockMvc.perform(post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUsers.write(new User(firstName: "Ivan",
                    country: belarusCountry)).getJson()))
                .andReturn()
                .getResponse()
        then:
            response.getStatus() == HttpStatus.BAD_REQUEST.value()
    }

    def "updateUser should return status 200 OK when user is updated"() {
        given:
            def savedUser = userRepository.save(new User(firstName: "Danil",
                lastName: "Shyshla",
                middleName: "Valerevich",
                sex: "male",
                phoneNumber: "+375111111111",
                email: "@gmail.com",
                country: belarusCountry))
        when:
            def response = mockMvc.perform(put(BASE_PATH + "/${savedUser.getId()}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUsers.write(new User(id: savedUser.getId(),
                    firstName: "Daniil",
                    lastName: "Shyshla",
                    sex: "male",
                    phoneNumber: "+375222222222",
                    email: "@gmail.com",
                    country: belarusCountry)).getJson()))
                .andReturn()
                .getResponse()
        then:
            response.getStatus() == HttpStatus.OK.value()
    }

    def "updateUser should return status 400 BAD REQUEST when user is updated"() {
        given:
            def savedUser = userRepository.save(new User(firstName: "Danil",
                lastName: "Shyshla",
                middleName: "Valerevich",
                sex: "male",
                phoneNumber: "+375111111111",
                email: "@gmail.com",
                country: belarusCountry))
        when:
            def response = mockMvc.perform(put(BASE_PATH + "/${savedUser.getId()}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUsers.write(new User(id: savedUser.getId(),
                    firstName: "Daniil",
                    country: belarusCountry)).getJson()))
                .andReturn()
                .getResponse()
        then:
            response.getStatus() == HttpStatus.BAD_REQUEST.value()
    }

    def "deleteUser should return status 200 OK when user is deleted"() {
        given:
            User savedUser = createNewUser()
        when:
            def response = mockMvc.perform(delete(BASE_PATH + "/${savedUser.getId()}")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
        then:
            response.getStatus() == HttpStatus.OK.value()
    }

    def "deleteUser should return status 404 NOT FOUND when used with given id is not found"() {
        when:
            def response = mockMvc.perform(delete(BASE_PATH + "/1"))
                .andReturn()
                .getResponse()
        then:
            response.getStatus() == HttpStatus.NOT_FOUND.value()
    }


    private def createNewUser() {
        return userRepository.save(new User("Danil", "Shyshla", 1L, "".getBytes()))
    }
}

/*
firstName: "Danil",
lastName: "Shyshla",
middleName: "Valerevich",
sex: "male",
phoneNumber: "+375111111111",
email: "@gmail.com",
country: belarusCountry*/
