package com.shallwecode.user.dto.request

data class UserCreateRequest(
    val email: String, //이메일
    val name: String, // 사용자 이름
    val nickname: String? = null, // 닉네임
    val password: String, // 비밀번호
    val phoneNumber: String, // 핸드폰 번호
    val profileImage: String? = null, // 프로필 사진 url
    val githubUrl: String? = null, // 깃허브 url
    val blogUrl: String? = null, // 개인 블로그 url
)

