package com.shallwecode.user.service

import com.shallwecode.common.exception.NotFoundDataException
import com.shallwecode.user.dto.request.UserCreateRequest
import com.shallwecode.user.entity.User
import com.shallwecode.user.entity.embeddable.Email
import com.shallwecode.user.entity.embeddable.Password
import com.shallwecode.user.entity.embeddable.PhoneNumber
import com.shallwecode.user.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
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
    var userRepository: UserRepository? = null
    var userService: UserService? = null

    @BeforeEach
    fun beforeEach() {
        userService = UserService(
            userRepository = this.userRepository ?: throw RuntimeException())
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
        val id = userService!!.createUser(request).id

        //then
        val userOptional = userRepository!!.findByIdOrNull(id)
        assertThat(userOptional).isNotNull

        userOptional?.let {
            assertThat(it.email).isEqualTo(Email(request.email))
            assertThat(it.name).isEqualTo(request.name)
            assertThat(it.nickname).isEqualTo(request.nickname)
            assertThat(it.password.matches(request.password)).isTrue
            assertThat(it.phoneNumber).isEqualTo(PhoneNumber(request.phoneNumber))
            assertThat(it.profileImage).isEqualTo(request.profileImage)
            assertThat(it.githubUrl).isEqualTo(request.githubUrl)
            assertThat(it.blogUrl).isEqualTo(request.blogUrl)
        } ?: assert(false) { "user is null" }
    }

    @Test
    fun `아이디로 사용자 정보 조회 성공`() {
        //given
        val userData = User(
            email = Email("test@gmail.com"),
            name = "name",
            nickname = "nickname",
            password = Password("password"),
            phoneNumber = PhoneNumber("01012341234"),
            profileImage = "imgUrl",
            blogUrl = "blogUrl",
            githubUrl = "gitUrl",
            deleted = false
        )
        val existUser =  userRepository!!.save(userData)

        //when
        val userModel = userService!!.findUser(existUser.id)

        //then
        userModel.let {
            assertThat(it.id).isEqualTo(existUser.id)
            assertThat(it.email).isEqualTo(existUser.email)
            assertThat(it.name).isEqualTo(existUser.name)
            assertThat(it.nickname).isEqualTo(existUser.nickname)
            assertThat(it.phoneNumber).isEqualTo(existUser.phoneNumber)
            assertThat(it.blogUrl).isEqualTo(existUser.blogUrl)
            assertThat(it.githubUrl).isEqualTo(existUser.githubUrl)
            assertThat(it.profileImage).isEqualTo(existUser.profileImage)
        }
    }

    @Test
    fun `아이디로 사용자 정보 조회 실패 - 해당 아이디의 정보가 존재하지 않는 경우`() {
        //given
        val id = 100L

        //when,then
        assertThatThrownBy { userService!!.findUser(id) }
            .isInstanceOf(NotFoundDataException::class.java)
            .hasMessage("해당 아이디의 사용자 정보를 찾을 수 없습니다. id : $id")
    }

    @Test
    fun `아이디로 사용자 정보 조회 성공 - 던져질 예외가 주어진 경우`() {
        //given
        val userData = User(
            email = Email("test@gmail.com"),
            name = "name",
            nickname = "nickname",
            password = Password("password"),
            phoneNumber = PhoneNumber("01012341234"),
            profileImage = "imgUrl",
            blogUrl = "blogUrl",
            githubUrl = "gitUrl",
            deleted = false
        )
        val existUser =  userRepository!!.save(userData)

        //when
        val userModel = userService!!.findUser(existUser.id, RuntimeException("test"))

        //then
        userModel.let {
            assertThat(it.id).isEqualTo(existUser.id)
            assertThat(it.email).isEqualTo(existUser.email)
            assertThat(it.name).isEqualTo(existUser.name)
            assertThat(it.nickname).isEqualTo(existUser.nickname)
            assertThat(it.phoneNumber).isEqualTo(existUser.phoneNumber)
            assertThat(it.blogUrl).isEqualTo(existUser.blogUrl)
            assertThat(it.githubUrl).isEqualTo(existUser.githubUrl)
            assertThat(it.profileImage).isEqualTo(existUser.profileImage)
        }
    }

    @Test
    fun `아이디로 사용자 정보 조회 실패 - 던져질 예외가 주어진 상태에서 해당 아이디의 정보가 존재하지 않는 경우`() {
        //given
        val id = 100L
        val message = "해당 아이디의 사용자 정보를 찾을 수 없습니다. id : $id"
        //when,then
        assertThatThrownBy { userService!!.findUser(id, NotFoundDataException(message)) }
            .isInstanceOf(NotFoundDataException::class.java)
            .hasMessage(message)
    }

    @Test
    fun `이메일로 사용자 정보 조회 성공 - 던져질 예외가 주어진 경우`() {
        //given
        val userData = User(
            email = Email("test@gmail.com"),
            name = "name",
            nickname = "nickname",
            password = Password("password"),
            phoneNumber = PhoneNumber("01012341234"),
            profileImage = "imgUrl",
            blogUrl = "blogUrl",
            githubUrl = "gitUrl",
            deleted = false
        )
        val existUser =  userRepository!!.save(userData)

        //when
        val userModel = userService!!.findUser(existUser.email, RuntimeException("test"))

        //then
        userModel.let {
            assertThat(it.id).isEqualTo(existUser.id)
            assertThat(it.email).isEqualTo(existUser.email)
            assertThat(it.name).isEqualTo(existUser.name)
            assertThat(it.nickname).isEqualTo(existUser.nickname)
            assertThat(it.phoneNumber).isEqualTo(existUser.phoneNumber)
            assertThat(it.blogUrl).isEqualTo(existUser.blogUrl)
            assertThat(it.githubUrl).isEqualTo(existUser.githubUrl)
            assertThat(it.profileImage).isEqualTo(existUser.profileImage)
        }
    }

    @Test
    fun `이메일로 사용자 정보 조회 실패 - 던져질 예외가 주어진 상태에서 해당 아이디의 정보가 존재하지 않는 경우`() {
        //given
        val email = Email("test@gmail.com")
        val message = "test : $email"
        //when,then
        assertThatThrownBy { userService!!.findUser(email, RuntimeException(message)) }
            .isInstanceOf(RuntimeException::class.java)
            .hasMessage(message)
    }

}