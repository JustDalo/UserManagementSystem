package com.dalo.usermanagementsystem.service.impl

import com.dalo.usermanagementsystem.dao.UserRepository
import com.dalo.usermanagementsystem.exception.ResourceNotFoundException
import com.dalo.usermanagementsystem.model.User
import com.dalo.usermanagementsystem.service.UserService
import lombok.RequiredArgsConstructor
import java.util.*

@RequiredArgsConstructor
class UserServiceImpl(private var userRepository: UserRepository) : UserService {

    override fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    override fun getUserById(id: Long): Optional<User> {
        var user = userRepository.findById(id)
        if (user.isPresent) {
            return user
        }
        throw ResourceNotFoundException("User", "Id", id)
    }

    override fun createUser(user: User): User {
        return userRepository.save(user)
    }

    override fun deleteUserById(id: Long) {

    }
}