package com.dalo.usermanagement.service.impl

import com.dalo.usermanagement.dao.UserRepository
import com.dalo.usermanagement.dto.UserDtoFromClient
import com.dalo.usermanagement.dto.UserDtoToClient
import com.dalo.usermanagement.exception.ResourceNotFoundException
import com.dalo.usermanagement.mapping.FromUserMapper
import com.dalo.usermanagement.mapping.ToUserMapper
import com.dalo.usermanagement.model.User
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import spock.lang.Specification

class UserServiceTest extends Specification {
    User[] users = [
        new User("Daniil", "Shyshla", 1L),
        new User("Danil", "Shyshlo", 1L)
    ]

    UserDtoToClient[] usersDto = [
        new UserDtoToClient(1L, "Danil", "Shyshla", new URL("http://localhost/api/users/1/image")),
        new UserDtoToClient(2L, "Daniil", "Shyshlo", new URL("http://localhost/api/users/2/image"))
    ]

    def userRepository = Mock(UserRepository)

    def fromUserMapper = new FromUserMapper()

    def toUserMapper = new ToUserMapper()

    def userService = new UserServiceImpl(userRepository, fromUserMapper, toUserMapper)

    def "getAllUsers should return all users"() {
        given:
            1 * userRepository.findAll() >> users
        when:
            def resultListOfUsers = userService.getAllUsers()
        then:
            resultListOfUsers.equals(usersDto.toList())
    }

    def "getUserById should return user with given id"() {
        given:
            def id = 1L
            1 * userRepository.findById(id) >> Optional.of(users[0])
        when:
            def resultUser = userService.getUserById(1L)
        then:
            resultUser.equals(usersDto[0])
    }

    def "createUser should return created user"() {
        given:
            def userDtoFromClient = UserDtoFromClient.builder()
                .id(1L)
                .firstName("Danil")
                .lastName("Shyshla")
                .middleName("Valerevich")
                .sex("male")
                .phoneNumber("+375000000000")
                .email("@gmail.com")
                .image("image".getBytes())
                .country(COUNTRY)
                .build()

            def imageMultipartFile = new MockMultipartFile(
                "file",
                "image.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "image".getBytes()
            )
            1 * userRepository.save(fromUserMapper.mapToUser(userDtoFromClient)) >> fromUserMapper.mapToUser(userDtoFromClient)
        when:
            UserDtoFromClient returnedUser = userService.createUser(userDtoFromClient, COUNTRY, imageMultipartFile)
        then:
            returnedUser == userDtoFromClient
    }

    def "update user should return updated user"() {
        given:
            def id = 1L
            def userDtoFromClient = UserDtoFromClient.builder()
                .id(1L)
                .firstName("Danil")
                .lastName("Shyshla")
                .middleName("Valerevich")
                .sex("male")
                .phoneNumber("+375000000000")
                .email("@gmail.com")
                .country(COUNTRY)
                .build()
            1 * userRepository.findById(id) >> Optional.of(users[0])
        when:
            UserDtoFromClient updatedUser = userService.updateUser(userDtoFromClient, userDtoFromClient.getId())
        then:
            updatedUser == userDtoFromClient
    }

    def "get userById should return ResourceNotFoundException"() {
        given:
            def id = 1000L
            1 * userRepository.findById(id) >> Optional.empty()
        when:
            userService.getUserById(id)
        then:
            thrown(ResourceNotFoundException)
    }
}
