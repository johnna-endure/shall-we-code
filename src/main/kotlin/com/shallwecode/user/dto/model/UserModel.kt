package com.shallwecode.user.dto.model

import com.shallwecode.user.entity.embeddable.Email

/**
 * User 엔티티의 조회용 데이터를 담는 클래스
 */
data class UserModel(
    val id: Long, // 아아디
    val email: Email, // 이메일
    val name: String, // 사용자 이름
    val nickname: String?, // 닉네임
    var phoneNumber: String, // 핸드폰 번호
    var profileImage: String?, // 프로필 사진 url
    var githubUrl: String?, // 깃허브 url
    var blogUrl: String?, // 개인 블로그 url
)
