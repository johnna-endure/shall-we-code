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
        val user = User(
            email = Email(email),
            name = name,
            password = Password(password),
            phoneNumber = PhoneNumber(phoneNumber)
        )

        // then
        assertThat(user.email.value).isEqualTo(email)
        assertThat(user.name).isEqualTo(name)
        assertThat(user.password.matches(password)).isTrue
        assertThat(user.phoneNumber.value).isEqualTo(phoneNumber)
        assertThat(user.joinedProjects.isEmpty()).isTrue

        assertThat(user.nickname).isNull()
        assertThat(user.profileImageUrl).isNull()
        assertThat(user.githubUrl).isNull()
        assertThat(user.blogUrl).isNull()
        assertThat(user.deleted).isFalse
        assertThat(user.createDateTime).isNotNull
        assertThat(user.updateDateTime).isNotNull
    }

    @Test
    fun `user 엔티티에 id가 할당되지 않았을 때, id 조회`() {
        // given
        val email = "test@gmail.com"
        val name = "cws"
        val password = "password"
        val phoneNumber = "01011112222"

        val user = User(
            email = Email(email),
            name = name,
            password = Password(password),
            phoneNumber = PhoneNumber(phoneNumber)
        )

        // when
        assertThrows<EmptyIdEntityException> { user.id }
    }
}