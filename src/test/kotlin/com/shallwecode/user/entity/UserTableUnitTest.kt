package com.shallwecode.user.entity

import com.shallwecode.common.exception.entity.EmptyIdEntityException
import com.shallwecode.user.entity.embeddable.Email
import com.shallwecode.user.entity.embeddable.Password
import com.shallwecode.user.entity.embeddable.PhoneNumber
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserTableUnitTest {

    @Test
    fun `user 객체 생성 테스트 - 필수값만 입력`() {
        // given
        val email = "test@gmail.com"
        val name = "cws"
        val password = "password"
        val phoneNumber = "01011112222"

        // when
        val userTable = UserTable(
            email = Email(email),
            name = name,
            password = Password(password),
            phoneNumber = PhoneNumber(phoneNumber)
        )

        // then
        assertThat(userTable.email.value).isEqualTo(email)
        assertThat(userTable.name).isEqualTo(name)
        assertThat(userTable.password.matches(password)).isTrue
        assertThat(userTable.phoneNumber.value).isEqualTo(phoneNumber)

        assertThat(userTable.nickname).isNull()
        assertThat(userTable.profileImageUrl).isNull()
        assertThat(userTable.githubUrl).isNull()
        assertThat(userTable.blogUrl).isNull()
        assertThat(userTable.deleted).isFalse
        assertThat(userTable.createDateTime).isNotNull
        assertThat(userTable.updateDateTime).isNotNull
    }

    @Test
    fun `user 엔티티에 id가 할당되지 않았을 때, id 조회`() {
        // given
        val email = "test@gmail.com"
        val name = "cws"
        val password = "password"
        val phoneNumber = "01011112222"

        val userTable = UserTable(
            email = Email(email),
            name = name,
            password = Password(password),
            phoneNumber = PhoneNumber(phoneNumber)
        )

        // when
        assertThrows<EmptyIdEntityException> { userTable.id }
    }
}