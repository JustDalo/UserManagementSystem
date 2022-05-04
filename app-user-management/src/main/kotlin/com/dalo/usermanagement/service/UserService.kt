package com.dalo.usermanagement.service

import com.dalo.model.User
import org.springframework.stereotype.Service

@Service
interface UserService {
    fun getAllUsers(): List<User>
    fun getUserById(id: Long): User
    fun createUser(user: User): User
    fun deleteUserById(id: Long)
    fun updateUser(user: User?, id: Long?): User?
}