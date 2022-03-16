package com.shallwecode.user.dto.model

import com.fasterxml.jackson.annotation.JsonUnwrapped
import com.shallwecode.user.entity.embeddable.Email

/**
 * User 엔티티의 조회용 데이터를 담는 클래스
 */
data class UserModel(
    val id: Long, // 아이디
    val email: Email, // 이메일
    val name: String, // 사용자 이름
    val nickname: String?, // 닉네임
    val phoneNumber: String, // 핸드폰 번호
    val profileImage: String?, // 프로필 사진 url
    val githubUrl: String?, // 깃허브 url
    val blogUrl: String?, // 개인 블로그 url
)
