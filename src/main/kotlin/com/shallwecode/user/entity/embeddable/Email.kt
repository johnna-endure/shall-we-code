package com.shallwecode.user.entity.embeddable

import com.shallwecode.exception.BadRequestException
import javax.persistence.Embeddable

@Embeddable
class Email(var email: String) {

    init {
        if(!validateEmail(email)) {
            throw BadRequestException("이메일이 유효하지 않습니다. email : $email")
        }
    }

    private fun validateEmail(email: String) : Boolean {
        val regex = """^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*(\.[a-zA-Z])*${'$'}""".toRegex()
        return regex.matches(email)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Email

        if (email != other.email) return false

        return true
    }

    override fun hashCode(): Int {
        return email.hashCode()
    }
}