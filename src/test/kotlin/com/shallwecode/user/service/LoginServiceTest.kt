package com.shallwecode.user.service

import com.shallwecode.user.dto.request.LoginRequest
import com.shallwecode.user.entity.User
import com.shallwecode.user.entity.embeddable.Email
import com.shallwecode.user.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.transaction.annotation.Transactional

/**
 * Mock없이 테스트 DB(h2)와 연결돼 메서드들을 테스트합니다.
 * 각 테스트들은 @Transactional 의 기능으로 테스트 후 롤백됩니다.
 */
@Transactional
@DataJpaTest
class LoginServiceTest {

    @Autowired
    var userRepository: UserRepository? = null

    @Autowired
    var loginService: LoginService? = null

    @BeforeEach
    fun beforeEach() {
        loginService = LoginService(userRepository!!)
    }

    @Test
    fun `테스트에 필요한 프로퍼티 바인딩 검증`() {
        assertThat(userRepository).isNotNull
        assertThat(loginService).isNotNull
    }

    @Test
    fun `로그인 성공`() {
        // given
        val email = "test@gmail.com"
        val password = "testpassword"
        val loginRequest = LoginRequest(email, password)

//        val existUser = User(
//            email = Email(email),
//            name = "cws"
//
//        )

//        userRepository.save()

        // when
//        loginService.login()
        // then
    }
}