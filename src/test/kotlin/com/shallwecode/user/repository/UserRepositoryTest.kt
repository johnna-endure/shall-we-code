package com.shallwecode.user.repository

import com.shallwecode.user.entity.*
import com.shallwecode.user.entity.embeddable.Email
import com.shallwecode.user.entity.embeddable.Password
import com.shallwecode.user.entity.embeddable.PhoneNumber
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class UserRepositoryTest(
    @Autowired
    val userRepository: UserRepository,
    @Autowired
    val entityManager: TestEntityManager
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

    @Test
    fun `save - joinProjects 필드를 제외한 필드 정보가 주어졌을 때,user 엔티티 저장 성공`() {
        // given
        val email = "test@gmail.com"
        val name = "cws"
        val password = "11112222"
        val phoneNumber = "01011112222"
        val nickname = "cws"
        val profileImageUrl = "imageUrl"
        val githubUrl = "githubUrl"
        val blogUrl = "blogUrl"

        // when
        val savedUser = userRepository.saveAndFlush(
            User(
                email = Email(email),
                name = name,
                password = Password(password),
                phoneNumber = PhoneNumber(phoneNumber),
                nickname = nickname,
                profileImageUrl = profileImageUrl,
                githubUrl = githubUrl,
                blogUrl = blogUrl,
            )
        )

        // then
        assertThat(savedUser.id).isNotNull
        assertThat(savedUser.email.value).isEqualTo(email)
        assertThat(savedUser.name).isEqualTo(name)
        assertThat(savedUser.password.matches(password)).isTrue
        assertThat(savedUser.nickname).isEqualTo(nickname)
        assertThat(savedUser.profileImageUrl).isEqualTo(profileImageUrl)
        assertThat(savedUser.githubUrl).isEqualTo(githubUrl)
        assertThat(savedUser.blogUrl).isEqualTo(blogUrl)
    }

    @Test
    fun `user 저장시 joinProjects 영속성 전이 테스트`() {

        // given
        val email = "test@gmail.com"
        val name = "cws"
        val password = "11112222"
        val phoneNumber = "01011112222"
        var savedUser = userRepository.save(
            User(
                email = Email(email),
                name = name,
                password = Password(password),
                phoneNumber = PhoneNumber(phoneNumber),
            )
        )

        // when
        val userProjects = mutableListOf(
            UserProject(UserProjectId(savedUser.id, 1L)),
            UserProject(UserProjectId(savedUser.id, 2L)),
        )
        savedUser.joinProject(*userProjects.toTypedArray())
        savedUser = userRepository.saveAndFlush(savedUser)

        // then
        assertThat(savedUser.joinedProjects).isNotEmpty
        assertThat(savedUser.joinedProjects[0].id.projectId).isEqualTo(1L)
        assertThat(savedUser.joinedProjects[1].id.projectId).isEqualTo(2L)
    }

    @Test
    fun `user의 joinProjects 아이템 하나를 제거했을 때, 영속성 전이 테스트`() {

        // given
        val email = "test@gmail.com"
        val name = "cws"
        val password = "11112222"
        val phoneNumber = "01011112222"
        var user = userRepository.save(
            User(
                email = Email(email),
                name = name,
                password = Password(password),
                phoneNumber = PhoneNumber(phoneNumber),
            )
        )

        val userProjects = mutableListOf(
            UserProject(UserProjectId(user.id, 1L)),
            UserProject(UserProjectId(user.id, 2L)),
        )
        user.joinProject(*userProjects.toTypedArray())
        user = userRepository.saveAndFlush(user)

        // when
        user.leaveProject(UserProjectId(user.id, 2L))
        user = userRepository.saveAndFlush(user)

        // then
        val ret = userRepository.findById(user.id).get()
        assertThat(ret.joinedProjects.size).isEqualTo(1)
    }
}