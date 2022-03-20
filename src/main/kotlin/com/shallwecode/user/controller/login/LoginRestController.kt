package com.shallwecode.user.controller.login

import com.shallwecode.common.http.response.HttpResponse
import com.shallwecode.user.dto.request.LoginRequest
import com.shallwecode.user.service.LoginService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import kotlin.math.log

@RestController
class LoginRestController(
    private val loginService: LoginService
) {

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): HttpResponse<Unit> {
        loginService.login(loginRequest)
        return HttpResponse(
            status = HttpStatus.OK.value(),
            message = "success"
        )
    }
}