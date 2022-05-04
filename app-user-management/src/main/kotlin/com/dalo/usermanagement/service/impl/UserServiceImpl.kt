package com.dalo.usermanagement.service.impl

import com.dalo.usermanagement.dao.UserRepository
import com.dalo.usermanagement.exception.ResourceNotFoundException
import com.dalo.usermanagement.service.UserService
import lombok.RequiredArgsConstructor
import com.dalo.usermanagement.model.User
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class UserServiceImpl(private var userRepository: UserRepository) : UserService {

    override fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    override fun getUserById(id: Long): User {
        val user = userRepository.findById(id)
        if (user.isPresent) {
            return user.get()
        }
        throw ResourceNotFoundException("User", "Id", id)
    }

    override fun createUser(user: User): User {
        return userRepository.save(user)
    }

    override fun deleteUserById(id: Long) {
        val user = userRepository.findById(id).orElseThrow { ResourceNotFoundException("User", "Id", id) }
        userRepository.delete(user)
    }

    override fun updateUser(user: User?, id: Long?): User {
        val existingUser = userRepository.findById(id!!).orElseThrow {
            ResourceNotFoundException(
                "User",
                "Id",
                id
            )
        }
        existingUser.setFirstName(user!!.getFirstName())
        existingUser.setLastName(user.getLastName())
        userRepository.save(existingUser)

        return existingUser
    }
}