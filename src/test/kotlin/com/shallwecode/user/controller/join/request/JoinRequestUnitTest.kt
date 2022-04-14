package com.shallwecode.user.controller.join.request

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class JoinRequestUnitTest {

    @Test
    fun `요청 정보로 User 엔티티 생성 테스트`() {
        // given
        val request = JoinRequest(
            email = "test@gmail.com",
            name = "cws",
            nickname = "nickname",
            password = "testpassword",
            phoneNumber = "0001112222",
            profileImage = null,
            githubUrl = "githubUrl",
            blogUrl = null
        )

        // when
        val user = request.toUserEntity()

        // then
        assertThat(user.email.email).isEqualTo(request.email)
        assertThat(user.name).isEqualTo(request.name)
        assertThat(user.nickname).isEqualTo(request.nickname)
        assertThat(user.password.matches(request.password))
        assertThat(user.phoneNumber.phoneNumber).isEqualTo(request.phoneNumber)
        assertThat(user.profileImage).isEqualTo(request.profileImage)
        assertThat(user.githubUrl).isEqualTo(request.githubUrl)
        assertThat(user.blogUrl).isEqualTo(request.blogUrl)
    }

}