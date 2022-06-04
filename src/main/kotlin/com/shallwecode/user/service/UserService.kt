package com.shallwecode.user.service

import com.shallwecode.common.exception.BadRequestException
import com.shallwecode.common.exception.NotFoundDataException
import com.shallwecode.common.util.modelmapper.ModelMapper
import com.shallwecode.user.controller.join.request.JoinRequest
import com.shallwecode.user.entity.User
import com.shallwecode.user.entity.embeddable.Email
import com.shallwecode.user.entity.embeddable.Password
import com.shallwecode.user.entity.embeddable.PhoneNumber
import com.shallwecode.user.entity.model.UserModel
import com.shallwecode.user.repository.UserRepository
import org.springframework.stereotype.Service

/**
 * 특별한 비지니스 로직없이 User CRUD 를 다루는 로직을 다루는 서비스입니다.
 */
@Service
class UserService(
    private val userRepository: UserRepository
) {

    private val modelMapper = ModelMapper()

    /**
     * user 데이터 저장
     * @param request 요청 데이터
     * @return 생성한 회원의 아이디 반환
     */
    fun createUser(request: JoinRequest): User {
        val user = User(
            email = Email(request.email),
            name = request.name,
            nickname = request.nickname,
            password = Password(request.password),
            phoneNumber = PhoneNumber(request.phoneNumber),
            profileImageUrl = request.profileImage,
            githubUrl = request.githubUrl,
            blogUrl = request.blogUrl
        )
        return userRepository.save(user)
    }


    /**
     * user 단건 조회
     *
     * @param id 회원 아이디
     * @return user 데이터 반환
     * @throws NoSuchElementException 회원 데이터가 없는 경우
     *
     */
    fun findUser(id: Long): UserModel {
        return userRepository.findById(id)
            .map { modelMapper.mapper<User, UserModel>(it) }
            .orElseThrow { NotFoundDataException("해당 아이디의 사용자 정보를 찾을 수 없습니다. id : $id") }
    }

    /**
     * user 단건 조회
     * @param email 회원 이메일
     * @return user 데이터 반환
     * @throws NotFoundDataException 회원 데이터가 없는 경우, 파라미터로 넘겨진 예외가 던져집니다.
     * @throws BadRequestException 이메일 형식 검증에 실패할 경우.
     *
     */
    fun findUser(email: String): UserModel {
        val user =
            userRepository.findByEmail(Email(email))
                ?: throw NotFoundDataException("해당 이메일의 사용자를 찾을 수 없습니다. email : $email");

        return user.let { modelMapper.mapper(it) }
    }
}