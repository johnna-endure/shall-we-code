package com.shallwecode.user.service

import com.ninjasquad.springmockk.MockkBean
import com.shallwecode.client.authentication.UserAuthenticationClient
import com.shallwecode.client.authentication.UserAuthenticationSaveResponseBody
import com.shallwecode.common.exception.NotFoundDataException
import com.shallwecode.user.controller.join.request.JoinRequest
import com.shallwecode.user.entity.embeddable.Email
import com.shallwecode.user.entity.embeddable.PhoneNumber
import com.shallwecode.user.entity.model.UserModel
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.transaction.annotation.Transactional
import java.io.IOException

@Transactional
@DataJpaTest
class JoinServiceTest {
    lateinit var joinService: JoinService

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
        val id = joinService.join(request)

        //then
        assertThat(id).isEqualTo(createdUser.id)
    }

    @Test
    fun `duplicateEmailCheck - 이메일이 중복인 경우`() {
        // given
        val email = "test@gmail.com"
        every { userService.findUser(email) } returns UserModel(
            id = 1L,
            email = Email(email),
            name = "name",
            nickname = "nickname",
            phoneNumber = PhoneNumber("01011112222"),
            profileImageUrl = "imageUrl",
            githubUrl = "gitUrl",
            blogUrl = "blogUrl"
        )

        // when
        val result = joinService.duplicateEmailCheck(email)

        // then
        assertThat(result.first).isEqualTo("duplicated")
        assertThat(result.second).isEqualTo(true)
    }

    @Test
    fun `duplicateEmailCheck - 이메일이 중복이 아닌 경우`() {
        // given
        val email = "test@gmail.com"
        every { userService.findUser(email) } throws NotFoundDataException()

        // when
        val result = joinService.duplicateEmailCheck(email)

        // then
        assertThat(result.first).isEqualTo("duplicated")
        assertThat(result.second).isEqualTo(false)
    }

    @Test
    fun `duplicateEmailCheck - 알 수 없는 예외로 실패한 경우`() {
        // given
        val email = "test@gmail.com"
        every { userService.findUser(email) } throws IOException()

        // NotFoundDataException 이외의 예외를 던질 경우, 던져지는 예외를 그대로 반환하는지 확인
        // when, then
        assertThrows<IOException> { joinService.duplicateEmailCheck(email) }
    }


}