package com.shallwecode.user.entity.embeddable

import com.shallwecode.common.exception.BadRequestException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.Embeddable

@Embeddable
class Password(var password: String) {

    init {
        validate(password)
        this.password = encoding(password)
    }

    private fun validate(password: String) {
        if (password.length < 8) throw BadRequestException("비밀번호는 8자리 이상이어야 합니다.")
    }

    private fun encoding(rawPassword: String): String {
        val encoder = BCryptPasswordEncoder()
        return encoder.encode(rawPassword)
    }

    fun matches(rawPassword: String): Boolean {
        val encoder = BCryptPasswordEncoder()
        return encoder.matches(rawPassword, this.password)
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Password

        if (password != other.password) return false

        return true
    }

    override fun hashCode(): Int {
        return password.hashCode()
    }


}