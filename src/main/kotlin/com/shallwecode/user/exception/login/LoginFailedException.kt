package com.shallwecode.user.exception.login

class LoginFailedException(message: String? = "로그인 실패"): RuntimeException(message){
}