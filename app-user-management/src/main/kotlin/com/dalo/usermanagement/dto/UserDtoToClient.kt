package com.dalo.usermanagement.dto

import java.net.URL

class UserDtoToClient(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val image: URL
) {
}