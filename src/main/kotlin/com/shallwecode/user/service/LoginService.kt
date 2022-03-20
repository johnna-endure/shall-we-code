package com.shallwecode.user.service

import com.shallwecode.user.dto.request.LoginRequest
import com.shallwecode.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PostMapping

@Service
class LoginService(
    private val userRepository: UserRepository
    ) {

    @PostMapping("/login")
    fun login(loginRequest: LoginRequest) {

    }

}