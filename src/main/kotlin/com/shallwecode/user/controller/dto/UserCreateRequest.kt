package com.shallwecode.user.controller.dto

import lombok.Builder

@Builder
data class UserCreateRequest(
    val name: String, // 사용자 이름
    val nickname: String, // 닉네임
    val password: String, // 비밀번호
    val phoneNumber: String, // 핸드폰 번호
    val profileImage: String, // 프로필 사진 url
)

