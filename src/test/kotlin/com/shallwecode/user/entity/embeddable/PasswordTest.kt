package com.shallwecode.user.entity.embeddable

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PasswordTest {

    @Test
    fun `패스워드 인코딩 값과 rawPassword 과 매치되는지 판정`() {
        val rawPassword = "hello"
        val encoded = Password(rawPassword)

        assertThat(encoded.matches(rawPassword)).isTrue
    }
}