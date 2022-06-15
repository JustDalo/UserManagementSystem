package com.dalo.usermanagementtest.service.impl

import com.dalo.usermanagementtest.dao.UserRepository
import com.dalo.usermanagementtest.dto.UserDtoFromClient
import com.dalo.usermanagementtest.dto.UserDtoToClient
import com.dalo.usermanagementtest.mapping.FromUserMapper
import com.dalo.usermanagementtest.mapping.ToUserMapper
import com.dalo.usermanagementtest.model.User
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.net.URL
import java.util.*


class UserServiceTest : BehaviorSpec({
    val mockUserRepository = mockk<UserRepository>()
    val toUserMapper = ToUserMapper()
    val fromUserMapper = FromUserMapper()
    val userService = UserServiceImpl(mockUserRepository, fromUserMapper, toUserMapper)

    val users = listOf(
        User(1L, "Daniil", "Shyshla", 1L, byteArrayOf()),
        User(2L, "Danil", "Shyshlo", 1L, byteArrayOf())
    )

    val usersDtoToClient = listOf(
        UserDtoToClient(1L, "Daniil", "Shyshla", URL("http://localhost:8080/api/users/1/image")),
        UserDtoToClient(2L, "Danil", "Shyshlo", URL("http://localhost:8080/api/users/2/image"))
    )

    val userDtoFromClient = UserDtoFromClient("Daniil", "Shyshla", byteArrayOf())

    Given("user repository for finding all") {
        every { mockUserRepository.findAll() } returns users
        When("find all users") {
            val resultListOfUsers = userService.getAllUsers()
            Then("getAllUsers should return all users") {
                resultListOfUsers.shouldBe(usersDtoToClient)
            }
        }
    }

    Given("user repository for finding by id") {
        every { mockUserRepository.findById(1L) } returns Optional.of(users[0])
        When("find user by id") {
            val resultUser = userService.getUserById(1L)
            Then("getUserById should return user with given id") {
                resultUser.shouldBe(usersDtoToClient[0])
            }
        }
    }

    Given("user repository for creating") {
        every { mockUserRepository.save(fromUserMapper.mapToUser(userDtoFromClient)) } returns users[0]
        When("create user") {
            val resultCreatedUser = userService.createUser(userDtoFromClient)
            Then("createUser should return created user") {
                resultCreatedUser.shouldBe(userDtoFromClient)
            }
        }
    }

    Given("user repository for updating user") {
        val updatedUser = UserDtoFromClient("Danil", "Shyshlo", byteArrayOf())
        every { mockUserRepository.findById(1L) } returns Optional.of(users[0])
        every {
            mockUserRepository.save(User(1L, "Danil", "Shyshlo", 1L, byteArrayOf()))
        } returns User(
            1L,
            "Danil",
            "Shyshlo",
            1L,
            byteArrayOf()
        )
        When("update user") {
            val resultUpdatedUsers = userService.updateUser(updatedUser, 1L)
            Then("update user should return updated user") {
                resultUpdatedUsers.shouldBe(updatedUser)
            }
        }
    }
})