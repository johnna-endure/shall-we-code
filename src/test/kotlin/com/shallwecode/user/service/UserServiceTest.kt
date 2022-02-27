package com.shallwecode.user.service

import com.shallwecode.user.repository.UserRepository
import jdk.jshell.spi.ExecutionControl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class UserServiceTest {
    @Autowired
    val repository: UserRepository? = null
    var userService: UserService? = null

    @BeforeEach
    fun beforeEach() {
        userService = UserService(userRepository = this.repository ?: throw RuntimeException())
    }

    @Test
    fun `테스트에 필요한 프로퍼티 바인딩`() {
        assertThat(repository).isNotNull
        assertThat(userService).isNotNull
    }

}