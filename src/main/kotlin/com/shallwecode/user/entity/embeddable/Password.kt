package com.shallwecode.user.entity.embeddable

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.Embeddable

@Embeddable
class Password(private var password: String) {

    init {
        this.password = encoding(password)
    }

    private fun encoding(password: String) : String {
        val encoder = BCryptPasswordEncoder()
        return encoder.encode(password)
    }
}