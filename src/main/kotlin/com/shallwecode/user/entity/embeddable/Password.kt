package com.shallwecode.user.entity.embeddable

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.Embeddable

@Embeddable
class Password(var password: String) {

    init {
        this.password = encoding(password)
    }

    private fun encoding(rawPassword: String) : String {
        val encoder = BCryptPasswordEncoder()
        return encoder.encode(rawPassword)
    }

    fun matches(rawPassword: String): Boolean {
        val encoder = BCryptPasswordEncoder()
        return encoder.matches(rawPassword, this.password)
    }
}