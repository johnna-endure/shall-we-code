package com.shallwecode.user.controller

import com.shallwecode.user.controller.dto.UserCreateRequest
import com.shallwecode.user.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class UserController(private val userService: UserService) {

    @PostMapping("/user")
    fun createUser(@RequestBody request: UserCreateRequest): Long? {
        return userService.createUser(request)
    }

}