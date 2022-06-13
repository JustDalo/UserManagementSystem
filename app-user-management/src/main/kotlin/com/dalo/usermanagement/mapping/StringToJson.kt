package com.dalo.usermanagement.mapping

import com.dalo.usermanagement.dto.UserDtoFromClient
import com.dalo.usermanagement.dto.UserDtoToClient
import com.dalo.usermanagement.model.User
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class StringToJson {
    fun getJson(user: String, file: MultipartFile): UserDtoFromClient {
        val mapper = ObjectMapper().registerModule(KotlinModule())
        var userJson: UserDtoFromClient = mapper.readValue(user, UserDtoFromClient::class.java)
        userJson.image = file.bytes
        return userJson
    }
}