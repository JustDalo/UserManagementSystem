package com.dalo.usermanagementtest.controller

import com.dalo.usermanagementtest.dto.UserDtoFromClient
import com.dalo.usermanagementtest.dto.UserDtoToClient
import com.dalo.usermanagementtest.mapping.StringToJson
import com.dalo.usermanagementtest.service.UserService
import com.dalo.usermanagementtest.model.User
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

    @PostMapping(
        consumes = [
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE
        ]
    )
    fun createUser(
        @RequestPart(value = "user") @Valid user: String,
        @RequestPart(value = "file", required = false) file: MultipartFile?,
    ): ResponseEntity<UserDtoFromClient> {
        val userFromClient: UserDtoFromClient = if (file !== null) {
            jsonMapper.getJson(user, file)
        } else {
            jsonMapper.getJson(user)
        }
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