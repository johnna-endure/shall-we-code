package com.shallwecode.user.service

import com.shallwecode.user.dto.model.UserModel
import com.shallwecode.user.dto.request.UserCreateRequest
import com.shallwecode.user.entity.User
import com.shallwecode.user.entity.embeddable.Email
import com.shallwecode.user.entity.embeddable.Password
import com.shallwecode.user.repository.UserRepository
import org.springframework.stereotype.Service
import java.io.IOException

@Service
class UserService(private val userRepository: UserRepository) {

    fun createUser(request: UserCreateRequest): Long {
        val user = User(
            Email(request.email),
            request.name,
            request.nickname,
            Password(request.password),
            request.phoneNumber,
            request.profileImage,
            request.githubUrl,
            request.blogUrl,
            false
        )
        return userRepository.save(user).id ?: throw IOException("저장된 엔티티의 아이디가 존재하지 않습니다.")
    }

//    fun getUser() : UserModel {
//
//    }
}