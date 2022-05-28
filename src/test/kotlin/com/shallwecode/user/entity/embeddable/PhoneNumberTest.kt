package com.shallwecode.user.entity.embeddable

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class PhoneNumberTest {

    @Test
    fun `하이픈이 포함되지 않은 11자리 숫자가 주어진 경우`() {
        val phoneNumber = "01011112222"
        assertThat(PhoneNumber(phoneNumber).value).isEqualTo(phoneNumber)
    }

    @Test
    fun `하이픈이 포함된 11자리 숫자가 주어진 경우`() {
        val phoneNumber = "010-1111-2222"
        val expectedPhoneNumber = "01011112222"
        assertThat(PhoneNumber(phoneNumber).value).isEqualTo(expectedPhoneNumber)
    }

    @Test
    fun `하이픈이 이외의 특수문자가 포함된 11자리 숫자가 주어진 경우`() {
        val phoneNumber = "010_1111!2222"
        assertThatThrownBy { PhoneNumber(phoneNumber) }
            .hasMessage("하이픈 이외의 문자는 포함될 수 없습니다. phoneNumber : $phoneNumber")
    }

    @Test
    fun `하이픈과 11자리 이상의 숫자가 주어진 경우`() {
        val phoneNumber = "010-11111-2222"
        val phoneNumberRemovedHyphen = phoneNumber.replace("-", "")
        assertThatThrownBy { PhoneNumber(phoneNumber) }
            .hasMessage("핸드폰 번호 숫자가 11자리 이상입니다. length : ${phoneNumberRemovedHyphen.length}")
    }

    @Test
    fun `하이픈 이외의 문자와 11자리 이상의 숫자가 주어진 경우`() {
        val phoneNumber = "010!@11111-222222"
        assertThatThrownBy { PhoneNumber(phoneNumber) }
            .hasMessage("하이픈 이외의 문자는 포함될 수 없습니다. phoneNumber : $phoneNumber")
    }
}