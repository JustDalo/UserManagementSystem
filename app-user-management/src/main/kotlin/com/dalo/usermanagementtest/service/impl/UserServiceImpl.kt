package com.dalo.usermanagementtest.service.impl

import com.dalo.usermanagementtest.dao.UserRepository
import com.dalo.usermanagementtest.dto.UserDtoFromClient
import com.dalo.usermanagementtest.dto.UserDtoToClient
import com.dalo.usermanagementtest.exception.ResourceNotFoundException
import com.dalo.usermanagementtest.mapping.FromUserMapper
import com.dalo.usermanagementtest.mapping.ToUserMapper
import com.dalo.usermanagementtest.service.UserService
import com.dalo.usermanagementtest.model.User
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val fromUserMapper: FromUserMapper,
    private val toUserMapper: ToUserMapper
) : UserService {

    override fun getAllUsers(): List<UserDtoToClient> {
        return userRepository.findAll().stream().map(toUserMapper::mapToUserDto).collect(Collectors.toList())
    }

    override fun getUserById(id: Long): UserDtoToClient {
        val user = userRepository.findById(id)
        if (user.isPresent) {
            return toUserMapper.mapToUserDto(user.get())
        }
        throw ResourceNotFoundException("User", "Id", id)
    }

    override fun getImageById(id: Long): User {
        val user: Optional<User> = userRepository.findById(id)
        if (user.isPresent) {
            return user.get()
        }
        throw ResourceNotFoundException("User", "Id", id)
    }

    override fun createUser(user: UserDtoFromClient): UserDtoFromClient {
        return fromUserMapper.mapToUserDto(userRepository.save(fromUserMapper.mapToUser(user)))
    }

    override fun deleteUserById(id: Long) {
        val user = userRepository.findById(id).orElseThrow { ResourceNotFoundException("User", "Id", id) }
        userRepository.delete(user)
    }

    override fun updateUser(user: UserDtoFromClient?, id: Long?): UserDtoFromClient {
        val existingUser: User = userRepository.findById(id!!).orElseThrow {
            ResourceNotFoundException(
                "User",
                "Id",
                id
            )
        }
        existingUser.setFirstName(user!!.firstName)
        existingUser.setLastName(user.lastName)
        existingUser.setImage(user.image)

        userRepository.save(existingUser)

        return fromUserMapper.mapToUserDto(existingUser)
    }
}