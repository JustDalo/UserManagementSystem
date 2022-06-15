package com.dalo.usermanagementtest.mapping

import com.dalo.usermanagementtest.model.User
import com.dalo.usermanagementtest.dto.UserDtoToClient
import org.springframework.stereotype.Component
import java.net.URL

@Component
class ToUserMapper {
    fun mapToUserDto(user: User): UserDtoToClient {
        return UserDtoToClient(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            URL("http", "localhost", 8080, "/api/users/" + user.getId() + "/image")
        )
    }
}