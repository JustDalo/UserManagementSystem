package com.dalo.usermanagementsystem.service.impl

import com.dalo.usermanagementsystem.dao.UserRepository
import com.dalo.usermanagementsystem.exception.ResourceNotFoundException
import com.dalo.usermanagementsystem.model.User
import com.dalo.usermanagementsystem.service.UserService
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import java.util.*

@Service
@RequiredArgsConstructor
class UserServiceImpl(private var userRepository: UserRepository) : UserService {

    override fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    override fun getUserById(id: Long): User {
        var user = userRepository.findById(id)
        if (user.isPresent) {
            return user.get()
        }
        throw ResourceNotFoundException("User", "Id", id)
    }

    override fun createUser(user: User): User {
        return userRepository.save(user)
    }

    override fun deleteUserById(id: Long) {
        var user = userRepository.findById(id).orElseThrow { ResourceNotFoundException("User", "Id", id) }
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
        existingUser.setLastName(user!!.getLastName())
        userRepository.save(existingUser)

        return existingUser
    }
}