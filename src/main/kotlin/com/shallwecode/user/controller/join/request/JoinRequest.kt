package com.shallwecode.user.controller.join.request

import com.shallwecode.user.entity.User
import com.shallwecode.user.entity.embeddable.Email
import com.shallwecode.user.entity.embeddable.Password
import com.shallwecode.user.entity.embeddable.PhoneNumber

data class JoinRequest(
    val email: String, //이메일
    val name: String, // 사용자 이름
    val nickname: String? = null, // 닉네임
    val password: String, // 비밀번호
    val phoneNumber: String, // 핸드폰 번호
    val profileImage: String? = null, // 프로필 사진 url
    val githubUrl: String? = null, // 깃허브 url
    val blogUrl: String? = null, // 개인 블로그 url
) {
    fun toUserEntity(): User {
        return User(
            email = Email(email),
            name = name,
            nickname = nickname,
            password = Password(password),
            phoneNumber = PhoneNumber(phoneNumber),
            profileImage = profileImage,
            githubUrl = githubUrl,
            blogUrl = blogUrl
        )
    }
}

