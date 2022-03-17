package com.shallwecode.user.entity.embeddable

import com.shallwecode.common.exception.BadRequestException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class EmailTest {

    @Test
    fun `이메일 형식을 모두 만족하는 경우`() {
        val email = "test@gmail.com"
        assertDoesNotThrow { Email(email) }
    }

    @Test
    fun `@ 앞에 마침표가 한 번 포함되는 경우`() {
        val email = "tes.t@gmail.com"
        assertDoesNotThrow { Email(email) }
    }

    @Test
    fun `@ 앞에 마침표가 두 번 포함되는 경우`() {
        val email = "tes..t@gmail.com"
        assertThrows<BadRequestException> { Email(email) }
    }

    @Test
    fun `@ 뒤에 마침표가 없는 경우`() {
        val email = "test@gmail"
        assertDoesNotThrow { Email(email) }
    }

    @Test
    fun `대문자가 포함되는 경우`() {
        val email = "test@GoogleR.com"
        assertDoesNotThrow { Email(email) }
    }

    @Test
    fun `하이픈(-)가 포함되는 경우`() {
        val email = "tes-t@GoogleR.com"
        assertDoesNotThrow { Email(email) }
    }

    @Test
    fun `언더바(_)가 포함되는 경우`() {
        val email = "tes_t@GoogleR.com"
        assertDoesNotThrow { Email(email) }
    }

    @Test
    fun `@앞에 비어있는 경우`() {
        val email = "@gmail.com"
        assertThrows<BadRequestException> { Email(email) }
    }

    @Test
    fun `@뒤가 비어있는 경우`() {
        val email = "test@"
        assertThrows<BadRequestException> { Email(email) }
    }


    @Test
    fun `@가 포함되지 않는 경우`() {
        val email = "testgoogle.com"
        assertThrows<BadRequestException> { Email(email) }
    }

}