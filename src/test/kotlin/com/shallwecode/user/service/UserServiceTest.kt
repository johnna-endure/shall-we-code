package com.shallwecode.user.service

import com.shallwecode.user.controller.dto.UserCreateRequest
import com.shallwecode.user.entity.embeddable.Email
import com.shallwecode.user.entity.embeddable.Password
import com.shallwecode.user.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@Transactional
@DataJpaTest
class UserServiceTest {
    @Autowired
    val userRepository: UserRepository? = null
    var userService: UserService? = null

    @BeforeEach
    fun beforeEach() {
        userService = UserService(userRepository = this.userRepository ?: throw RuntimeException())
    }

    @Test
    fun `테스트에 필요한 프로퍼티 바인딩`() {
        assertThat(userRepository).isNotNull
        assertThat(userService).isNotNull
    }

    @Test
    fun `사용자 생성 성공 - 요청 데이터가 모두 올바른 경우`() {
        //given
        val request = UserCreateRequest(
            email = "test@gmail.com",
            name = "name",
            nickname = "nickname",
            password = "password",
            phoneNumber = "01011112222",
            profileImage = "imageUrl",
            githubUrl = "gitUrl",
            blogUrl = "blogUrl"
        )

        //when
        val id = userService?.createUser(request)

        //then
        val userOptional = userRepository?.findByIdOrNull(id)
        assertThat(userOptional).isNotNull

        userOptional?.let {
            assertThat(it.email).isEqualTo(Email(request.email))
            assertThat(it.name).isEqualTo(request.name)
            assertThat(it.nickname).isEqualTo(request.nickname)
            assertThat(it.password.matches(request.password)).isTrue
            assertThat(it.phoneNumber).isEqualTo(request.phoneNumber)
            assertThat(it.profileImage).isEqualTo(request.profileImage)
            assertThat(it.githubUrl).isEqualTo(request.githubUrl)
            assertThat(it.blogUrl).isEqualTo(request.blogUrl)
        } ?: assert(false) { "user is null" }
    }
}