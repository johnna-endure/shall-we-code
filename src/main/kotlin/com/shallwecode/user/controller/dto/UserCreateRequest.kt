package com.shallwecode.user.controller.dto

data class UserCreateRequest(
    val email: String, //이메일
    val name: String, // 사용자 이름
    val nickname: String, // 닉네임
    val password: String, // 비밀번호
    val phoneNumber: String, // 핸드폰 번호
    val profileImage: String, // 프로필 사진 url
    val githubUrl: String, // 깃허브 url
    val blogUrl: String, // 개인 블로그 url
)

