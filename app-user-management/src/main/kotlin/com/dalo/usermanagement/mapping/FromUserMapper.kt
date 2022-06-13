package com.dalo.usermanagement.mapping

import com.dalo.usermanagement.dto.UserDtoFromClient
import com.dalo.usermanagement.model.User
import org.springframework.stereotype.Component

@Component
class FromUserMapper {
    fun mapToUserDto(user: User): UserDtoFromClient {
        return UserDtoFromClient(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getImage()
        )
    }

    fun mapToUser(userDtoFromClient: UserDtoFromClient): User {
        return User(
            userDtoFromClient.firstName,
            userDtoFromClient.lastName,
            1,
            userDtoFromClient.image
        )
    }
}