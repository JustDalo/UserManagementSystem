package com.dalo.usermanagementtest.service

import com.dalo.usermanagementtest.dto.UserDtoFromClient
import com.dalo.usermanagementtest.dto.UserDtoToClient
import com.dalo.usermanagementtest.model.User
import org.springframework.stereotype.Service

@Service
interface UserService {
    fun getAllUsers(): List<UserDtoToClient>
    fun getUserById(id: Long): UserDtoToClient
    fun getImageById(id: Long): User
    fun createUser(user: UserDtoFromClient): UserDtoFromClient
    fun deleteUserById(id: Long)
    fun updateUser(user: UserDtoFromClient?, id: Long?): UserDtoFromClient?
}