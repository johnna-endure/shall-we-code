package com.shallwecode.user.service.join

import com.shallwecode.client.authentication.UserAuthenticationClient
import com.shallwecode.client.authentication.request.UserAuthenticationRequest
import com.shallwecode.client.exception.ClientException
import com.shallwecode.user.controller.join.request.JoinRequest
import com.shallwecode.user.repository.UserRepository
import com.shallwecode.user.service.UserService
import org.springframework.stereotype.Service

/**
 * 사용자 회원가입 관련 로직을 다루는 서비스입니다.
 */
@Service
class JoinService(
    private val userService: UserService,
    private val userAuthenticationClient: UserAuthenticationClient
    ){

    /**
     * 사용자 회원가입 처리
     * @param request
     * @return 저장된 사용자의 id를 반환
     */
    fun join(request: JoinRequest): Long {
        val createdUser = userService.createUser(request)

        val authenticationRequest = UserAuthenticationRequest(
            userId = createdUser.id,
            email = createdUser.email.email,
            password = createdUser.password.password,
            roles = listOf("user"),
            createDateTime = createdUser.createDateTime
        )
        return userAuthenticationClient.saveUserAuthentication(authenticationRequest)?.userId
            ?: throw ClientException("userAuthenticationClient.saveUserAuthentication failed")
    }
}