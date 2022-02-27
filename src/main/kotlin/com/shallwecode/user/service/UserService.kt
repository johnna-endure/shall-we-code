package com.shallwecode.user.service

import com.shallwecode.user.controller.dto.UserCreateRequest
import com.shallwecode.user.entity.User
import com.shallwecode.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun createUser(request: UserCreateRequest): Long? {
        val user = User(
            request.name,
            request.nickname,
            request.password,
            request.phoneNumber,
            request.profileImage,
            false
        )
        return userRepository.save(user).id
    }
}