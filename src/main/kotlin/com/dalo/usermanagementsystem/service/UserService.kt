package com.dalo.usermanagementsystem.service

import com.dalo.usermanagementsystem.model.User
import org.springframework.stereotype.Service
import java.util.*

@Service
interface UserService {
    fun getAllUsers(): List<User>
    fun getUserById(id: Long): User
    fun createUser(user: User): User
    fun deleteUserById(id: Long)
    fun updateUser(user: User?, id: Long?): User?
}