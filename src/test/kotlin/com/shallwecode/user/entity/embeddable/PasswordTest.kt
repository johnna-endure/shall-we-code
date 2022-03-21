package com.shallwecode.user.entity.embeddable

import com.shallwecode.common.exception.BadRequestException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class PasswordTest {

    @Test
    fun `패스워드 인코딩 값과 rawPassword 과 매치되는지 판정`() {
        val rawPassword = "hello1231412"
        val encoded = Password(rawPassword)

        assertThat(encoded.matches(rawPassword)).isTrue
    }

    @Test
    fun `패스워드가 8자리 이하인 경우`() {
        val message = "비밀번호는 8자리 이상이어야 합니다."
        val rawPassword = "hello"
        assertThatThrownBy { Password(rawPassword) }
            .isInstanceOf(BadRequestException::class.java)
            .hasMessage(message)

    }
}