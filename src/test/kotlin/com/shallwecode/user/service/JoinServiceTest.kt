package com.shallwecode.user.service

import com.ninjasquad.springmockk.MockkBean
import com.shallwecode.client.authentication.UserAuthenticationClient
import com.shallwecode.client.authentication.UserAuthenticationSaveResponseBody
import com.shallwecode.user.controller.join.request.JoinRequest
import com.shallwecode.user.service.join.JoinService
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@DataJpaTest
class JoinServiceTest {
    var joinService: JoinService? = null

    @MockkBean
    lateinit var userAuthenticationClient: UserAuthenticationClient

    @MockkBean
    lateinit var userService: UserService

    @BeforeEach
    fun beforeEach() {
        joinService = JoinService(
            userService,
            userAuthenticationClient
        )
    }

    @Test
    fun `테스트에 필요한 프로퍼티 바인딩`() {
        assertThat(userService).isNotNull
        assertThat(joinService).isNotNull
    }

    @Test
    fun `회원 가입 성공 - 요청 데이터가 모두 올바른 경우`() {
        //given
        val request = JoinRequest(
            email = "test@gmail.com",
            name = "name",
            nickname = "nickname",
            password = "password",
            phoneNumber = "01011112222",
            profileImage = "imageUrl",
            githubUrl = "gitUrl",
            blogUrl = "blogUrl"
        )

        val createdUser = request.toUserEntity()
        createdUser._id = 1L

        every { userAuthenticationClient.saveUserAuthentication(any()) } returns UserAuthenticationSaveResponseBody(1L)
        every { userService.createUser(any()) } returns createdUser

        //when
        val id = joinService!!.join(request)

        //then
        assertThat(id).isEqualTo(createdUser.id)
    }

}