package com.dalo.usermanagementtest.mapping

import com.dalo.usermanagementtest.dto.UserDtoFromClient
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class StringToJson {
    fun getJson(user: String, file: MultipartFile): UserDtoFromClient {
        val mapper = jacksonObjectMapper()
        val userJson: UserDtoFromClient = mapper.readValue(user, UserDtoFromClient::class.java)
        userJson.image = file.bytes
        return userJson
    }

    fun getJson(user: String): UserDtoFromClient {
        val mapper = jacksonObjectMapper()
        val userJson: UserDtoFromClient = mapper.readValue(user, UserDtoFromClient::class.java)
        userJson.image = byteArrayOf()
        return userJson
    }
}