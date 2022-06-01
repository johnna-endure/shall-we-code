package com.shallwecode.user.repository

import com.shallwecode.project.entity.JoinProject
import com.shallwecode.project.entity.JoinProjectId
import com.shallwecode.project.repository.ProjectRepository
import com.shallwecode.user.entity.UserTable
import com.shallwecode.user.entity.embeddable.Email
import com.shallwecode.user.entity.embeddable.Password
import com.shallwecode.user.entity.embeddable.PhoneNumber
import com.shallwecode.user.entity.joinProject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.transaction.annotation.Transactional

@DataJpaTest
@Transactional
class UserTableRepositoryTest(
    @Autowired
    val userRepository: UserRepository,
    @Autowired
    val projectRepository: ProjectRepository
) {

    @Test
    fun `테스트에 필요한 프로퍼티 바인딩`() {
        assertThat(userRepository).isNotNull
    }

    @Test
    fun `findByEmail - 이메일로 조회에 성공하는 경우`() {
        //given
        val savedUserTable = userRepository.save(
            UserTable(
                email = Email("test@gmail.com"),
                name = "cws",
                password = Password("11112222"),
                phoneNumber = PhoneNumber("01011112222"),
            )
        )
        //when
        val found = userRepository.findByEmail(savedUserTable.email)!!
        //then
        assertThat(found.email).isEqualTo(savedUserTable.email)
        assertThat(found.name).isEqualTo(savedUserTable.name)
        assertThat(found.password).isEqualTo(savedUserTable.password)
        assertThat(found.phoneNumber).isEqualTo(savedUserTable.phoneNumber)
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
        val savedUserTable = userRepository.saveAndFlush(
            UserTable(
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
        assertThat(savedUserTable.id).isNotNull
        assertThat(savedUserTable.email.value).isEqualTo(email)
        assertThat(savedUserTable.name).isEqualTo(name)
        assertThat(savedUserTable.password.matches(password)).isTrue
        assertThat(savedUserTable.nickname).isEqualTo(nickname)
        assertThat(savedUserTable.profileImageUrl).isEqualTo(profileImageUrl)
        assertThat(savedUserTable.githubUrl).isEqualTo(githubUrl)
        assertThat(savedUserTable.blogUrl).isEqualTo(blogUrl)
    }

    @Test
    fun `joinProject - 프로젝트 아이디 저장 성공 여부`() {

        // given
        val email = "test@gmail.com"
        val name = "cws"
        val password = "11112222"
        val phoneNumber = "01011112222"
        var savedUserTable = userRepository.save(
            UserTable(
                email = Email(email),
                name = name,
                password = Password(password),
                phoneNumber = PhoneNumber(phoneNumber),
            )
        )

        // when
        val joinProjects = mutableListOf(
            JoinProject(JoinProjectId(savedUserTable.id, 1L)),
            JoinProject(JoinProjectId(savedUserTable.id, 2L)),
        )
        savedUserTable.joinProject(*joinProjects.toTypedArray())
        savedUserTable = userRepository.save(savedUserTable)

        // then
        assertThat(savedUserTable.joinedProjects).isNotEmpty
        assertThat(savedUserTable.joinedProjects[0].id.projectId).isEqualTo(1L)
        assertThat(savedUserTable.joinedProjects[1].id.projectId).isEqualTo(2L)
    }
}