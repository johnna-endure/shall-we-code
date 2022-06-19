package com.shallwecode.user.service

import com.shallwecode.client.authentication.UserAuthenticationClient
import com.shallwecode.client.authentication.request.UserAuthenticationRequest
import com.shallwecode.client.exception.ClientException
import com.shallwecode.common.exception.NotFoundDataException
import com.shallwecode.common.exception.entity.EmptyIdEntityException
import com.shallwecode.user.controller.join.request.JoinRequest
import org.springframework.stereotype.Service

/**
 * 사용자 회원가입 관련 로직을 다루는 서비스입니다.
 */
@Service
class JoinService(
    private val userService: UserService,
    private val userAuthenticationClient: UserAuthenticationClient
) {

    /**
     * 사용자 회원가입 처리
     * @param request
     * @return 저장된 사용자의 id를 반환
     */
    fun join(request: JoinRequest): Long {
        val createdUser = userService.createUser(request)

        val authenticationRequest = UserAuthenticationRequest(
            userId = createdUser.id ?: throw EmptyIdEntityException(),
            email = createdUser.email.value,
            password = createdUser.password.value,
            roles = listOf("user"),
            createDateTime = createdUser.createDateTime
        )

        return userAuthenticationClient.saveUserAuthentication(authenticationRequest)?.userId
            ?: throw ClientException("userAuthenticationClient.saveUserAuthentication failed")
    }

    /**
     * 회원가입 중 이메일 중복체크
     * @param email 사용자가 입력한 이메일
     * @return 이메일이 중복인 경우 Pair<duplicated: String, true: Boolean> 반환.
     * 중복이 아닌 경우 Pair<duplicated: String, false: Boolean> 를 반환
     *
     */
    fun duplicateEmailCheck(email: String): Pair<String, Boolean> {
        return try {
            userService.findUser(email) // 회원이 없는 경우, NotFoundDataException 던짐
            "duplicated" to true
        } catch (ex: NotFoundDataException) {
            "duplicated" to false
        }
    }
}