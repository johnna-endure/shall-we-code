package com.shallwecode.user.service.join

import com.shallwecode.user.dto.request.UserCreateRequest
import com.shallwecode.user.entity.User
import com.shallwecode.user.entity.embeddable.Email
import com.shallwecode.user.entity.embeddable.Password
import com.shallwecode.user.entity.embeddable.PhoneNumber
import com.shallwecode.user.repository.UserRepository
import com.shallwecode.user.service.UserService
import org.springframework.stereotype.Service

/**
 * 사용자 회원가입 관련 로직을 다루는 서비스입니다.
 */
@Service
class JoinService(
    private val userRepository: UserRepository): UserService(userRepository){

//    fun join(request: UserCreateRequest): Long {
//        createUser(request)
//    }
}