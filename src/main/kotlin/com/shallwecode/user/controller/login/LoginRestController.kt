package com.shallwecode.user.controller.login

import com.shallwecode.common.http.response.HttpResponse
import com.shallwecode.user.dto.request.LoginRequest
import com.shallwecode.user.service.login.LoginService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * 로그인 관련 API 를 다루는 컨트롤러입니다.
 *
 * 현재 사용하지 않음
 */
@RestController
class LoginRestController(
    private val loginService: LoginService
) {

    @ResponseStatus(OK)
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): HttpResponse<Unit> {
        loginService.login(loginRequest)
        return HttpResponse(
            message = "success"
        )
    }
}