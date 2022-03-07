package com.shallwecode.user.entity

import javax.persistence.Embeddable

@Embeddable
class Email(private val email: String) {

    fun isValid(email: String) {
        // TODO 정규표현식 테스트 하기
        val regex = """[a-z0-9]+@([a-z0-9]*\.)*([a-z0-9]+)"""
    }
}