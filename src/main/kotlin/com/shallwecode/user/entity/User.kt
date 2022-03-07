package com.shallwecode.user.entity

import lombok.Builder
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class User(
    var name: String, // 사용자 이름
    var nickname: String, // 닉네임
    var password: String, // 비밀번호
    var phoneNumber: String, // 핸드폰 번호
    var profileImage: String, // 프로필 사진 url
    var githubUrl: String, // 깃허브 url
    var blogUrl: String, // 개인 블로그 url
    var deleted: Boolean // 삭제 여부
) {
    @Id
    @GeneratedValue
    var id: Long? = null // 식별 아이디

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }


}