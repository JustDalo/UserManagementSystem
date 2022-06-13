package com.dalo.usermanagement.controller

import com.dalo.usermanagement.dto.UserDtoFromClient
import com.dalo.usermanagement.dto.UserDtoToClient
import com.dalo.usermanagement.mapping.StringToJson
import com.dalo.usermanagement.service.UserService
import com.dalo.usermanagement.model.User
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService, private val jsonMapper: StringToJson) {

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserDtoToClient> {
        return ResponseEntity<UserDtoToClient>(userService.getUserById(id), HttpStatus.OK)
    }

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserDtoToClient>> {
        return ResponseEntity<List<UserDtoToClient>>(userService.getAllUsers(), HttpStatus.OK)
    }

    @GetMapping("/{id}/image")
    fun getImageById(@PathVariable id: Long): ResponseEntity<User> {
        return ResponseEntity<User>(userService.getImageById(id), HttpStatus.OK)
    }

    @PostMapping(consumes = [
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.MULTIPART_FORM_DATA_VALUE
    ])
    fun createUser(
        @RequestPart(value = "user") @Valid user: String,
        @RequestPart(value = "file", required = false) file: MultipartFile,
        httpServletRequest: HttpServletRequest?
    ): ResponseEntity<UserDtoFromClient> {
        val userFromClient = jsonMapper.getJson(user, file)
        return ResponseEntity<UserDtoFromClient>(userService.createUser(userFromClient), HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: Long,
        @Valid @RequestBody user: UserDtoFromClient?
    ): ResponseEntity<UserDtoFromClient> {
        return ResponseEntity<UserDtoFromClient>(userService.updateUser(user, id), HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable id: Long): ResponseEntity<*> {
        userService.deleteUserById(id)
        return ResponseEntity<Any?>(HttpStatus.OK)
    }
}