package com.shallwecode.user.service.login

import com.shallwecode.common.exception.BadRequestException
import com.shallwecode.common.exception.NotFoundDataException
import com.shallwecode.user.dto.request.LoginRequest
import com.shallwecode.user.entity.embeddable.Email
import com.shallwecode.user.exception.login.LoginFailedException
import com.shallwecode.user.repository.UserRepository
import com.shallwecode.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PostMapping
import kotlin.math.log

@Service
class LoginService(
    private val userRepository: UserRepository
    ): UserService(userRepository) {

    fun login(loginRequest: LoginRequest) {
        val existUser = findUser(
            email = loginRequest.email,
            exception = LoginFailedException("이메일이 ${loginRequest.email}인 사용자가 존재하지 않습니다."))

        val isValidPassword = existUser.password.matches(loginRequest.password);
        if(!isValidPassword) throw LoginFailedException("비밀번호가 일치하지 않습니다.")
    }
    
}