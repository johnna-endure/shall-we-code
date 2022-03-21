package com.shallwecode.user.service

import com.shallwecode.user.dto.request.UserCreateRequest
import com.shallwecode.user.entity.embeddable.Email
import com.shallwecode.user.entity.embeddable.PhoneNumber
import com.shallwecode.user.repository.UserRepository
import com.shallwecode.user.service.join.JoinService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@Transactional
@DataJpaTest
class JoinServiceTest {
    @Autowired
    var userRepository: UserRepository? = null
    var joinService: JoinService? = null

    @BeforeEach
    fun beforeEach() {
        joinService = JoinService(
            userRepository = this.userRepository ?: throw RuntimeException())
    }

    @Test
    fun `테스트에 필요한 프로퍼티 바인딩`() {
        Assertions.assertThat(userRepository).isNotNull
        Assertions.assertThat(joinService).isNotNull
    }

    @Test
    fun `회원 가입 성공 - 요청 데이터가 모두 올바른 경우`() {
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
        val id = joinService!!.join(request)

        //then
        val userOptional = userRepository!!.findByIdOrNull(id)
        Assertions.assertThat(userOptional).isNotNull

        userOptional?.let {
            Assertions.assertThat(it.email).isEqualTo(Email(request.email))
            Assertions.assertThat(it.name).isEqualTo(request.name)
            Assertions.assertThat(it.nickname).isEqualTo(request.nickname)
            Assertions.assertThat(it.password.matches(request.password)).isTrue
            Assertions.assertThat(it.phoneNumber).isEqualTo(PhoneNumber(request.phoneNumber))
            Assertions.assertThat(it.profileImage).isEqualTo(request.profileImage)
            Assertions.assertThat(it.githubUrl).isEqualTo(request.githubUrl)
            Assertions.assertThat(it.blogUrl).isEqualTo(request.blogUrl)
        } ?: assert(false) { "user is null" }
    }

}