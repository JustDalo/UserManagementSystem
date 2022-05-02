package com.dalo.usermanagementsystem.controller

import com.dalo.usermanagementsystem.model.User
import com.dalo.usermanagementsystem.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/api/users")
class UserController(userService: UserService) {
    private val userService: UserService

    init {
        this.userService = userService
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> {
        return ResponseEntity<User>(userService.getUserById(id), HttpStatus.OK)
    }

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<User>> {
        return ResponseEntity<List<User>>(userService.getAllUsers(), HttpStatus.OK)
    }

    @PostMapping
    fun createUser(@Valid @RequestBody user: User, httpServletRequest: HttpServletRequest?): ResponseEntity<User> {
        return ResponseEntity<User>(userService.createUser(user), HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @Valid @RequestBody user: User?): ResponseEntity<User> {
        return ResponseEntity<User>(userService.updateUser(user, id), HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable id: Long): ResponseEntity<*> {
        userService.deleteUserById(id)
        return ResponseEntity<Any?>(HttpStatus.OK)
    }
}