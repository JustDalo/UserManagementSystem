package com.dalo.usermanagementsystem.service

import com.dalo.usermanagementsystem.model.User
import java.util.*

interface UserService {
    fun getAllUsers(): List<User>
    fun getUserById(id: Long): Optional<User>
    fun createUser(user: User): User
    fun deleteUserById(id: Long)
}