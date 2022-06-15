package com.dalo.usermanagementtest.mapping

import com.dalo.usermanagementtest.dto.UserDtoFromClient
import com.dalo.usermanagementtest.model.User
import org.springframework.stereotype.Component

@Component
class FromUserMapper {
    fun mapToUserDto(user: User): UserDtoFromClient {
        return UserDtoFromClient(
            user.getFirstName(),
            user.getLastName(),
            user.getImage()
        )
    }

    fun mapToUser(userDtoFromClient: UserDtoFromClient): User {
        return User(
            userDtoFromClient.firstName,
            userDtoFromClient.lastName,
            userDtoFromClient.image
        )
    }
}