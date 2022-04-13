package com.shallwecode.user.repository

import com.shallwecode.user.entity.User
import com.shallwecode.user.entity.embeddable.Email
import com.shallwecode.user.entity.embeddable.Password
import com.shallwecode.user.entity.embeddable.PhoneNumber
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.transaction.annotation.Transactional

@DataJpaTest
@Transactional
class UserRepositoryTest(
    @Autowired
    val userRepository: UserRepository
) {

    @Test
    fun `테스트에 필요한 프로퍼티 바인딩`() {
        assertThat(userRepository).isNotNull
    }

    @Test
    fun `findByEmail - 이메일로 조회에 성공하는 경우`() {
        //given
        val savedUser = userRepository.save(
            User(
                email = Email("test@gmail.com"),
                name = "cws",
                password = Password("11112222"),
                phoneNumber = PhoneNumber("01011112222"),
            )
        )
        //when
        val found = userRepository.findByEmail(savedUser.email)!!

        //then
        assertThat(found.email).isEqualTo(savedUser.email)
        assertThat(found.name).isEqualTo(savedUser.name)
        assertThat(found.password).isEqualTo(savedUser.password)
        assertThat(found.phoneNumber).isEqualTo(savedUser.phoneNumber)
    }

    @Test
    fun `findByEmail - 이메일에 해당하는 데이터가 없는 경우`() {
        //given
        val email = Email("test@gmail.com")
        //when
        val found = userRepository.findByEmail(email)

        //then
        assertThat(found).isNull()
    }


}