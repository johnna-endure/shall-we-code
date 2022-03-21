package com.shallwecode.user.controller

import com.shallwecode.common.http.response.HttpResponse
import com.shallwecode.user.dto.model.UserModel
import com.shallwecode.user.dto.request.UserCreateRequest
import com.shallwecode.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * 다른 비지니스 로직없이 단순하게 User 정보에 접근하는 클래스입니다.
 */
@RestController
class UserRestController(private val userService: UserService) {

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody request: UserCreateRequest): HttpResponse<Map<String, Long>>  {
        return HttpResponse(
            status = HttpStatus.CREATED.value(),
            message = "created",
            body = mapOf("id" to userService.createUser(request).id) )
    }

    @GetMapping("/users/{id}")
    fun getUser(@PathVariable("id") id: Long): HttpResponse<UserModel>{
        return HttpResponse(
            status = HttpStatus.OK.value(),
            message = "success",
            body = userService.findUser(id)
        )
    }

}