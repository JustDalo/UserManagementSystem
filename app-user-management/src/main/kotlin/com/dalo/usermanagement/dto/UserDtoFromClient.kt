package com.dalo.usermanagement.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class UserDtoFromClient(
    val id: Long,
    val firstName: String,
    val lastName: String,
    var image: ByteArray = byteArrayOf()
) {
}