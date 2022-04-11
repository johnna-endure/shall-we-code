package com.shallwecode.user.controller.login

import com.shallwecode.common.http.response.HttpResponse
import com.shallwecode.user.exception.login.LoginFailedException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 *  * 현재 사용하지 않음

 */
@RestControllerAdvice(
    basePackageClasses = [LoginRestController::class],
    assignableTypes = [LoginRestController::class]
)
class LoginRestControllerAdvice {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(LoginFailedException::class)
    fun loginFailException(exception: LoginFailedException): HttpResponse<String> {
        return HttpResponse(
            message = exception.message ?: "로그인 실패."
        )
    }
}