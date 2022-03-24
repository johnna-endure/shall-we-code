package com.shallwecode.user.service

import com.shallwecode.common.exception.NotFoundDataException
import com.shallwecode.common.util.modelmapper.ModelMapper
import com.shallwecode.user.dto.model.UserModel
import com.shallwecode.user.dto.request.UserCreateRequest
import com.shallwecode.user.entity.User
import com.shallwecode.user.entity.embeddable.Email
import com.shallwecode.user.entity.embeddable.Password
import com.shallwecode.user.entity.embeddable.PhoneNumber
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
     * @param request
     * @return 생성한 회원의 아이디 반환
     */
    fun createUser(request: UserCreateRequest): User {
        val user = User(
            Email(request.email),
            request.name,
            request.nickname,
            Password(request.password),
            PhoneNumber(request.phoneNumber),
            request.profileImage,
            request.githubUrl,
            request.blogUrl,
            false
        )
        return userRepository.save(user)
    }

    /**
     * user 단건 조회
     *
     * @param id 회원 아이디
     * @return user 엔티티 조회용 모델 UserModel 반환
     * @throws NoSuchElementException 회원 데이터가 없는 경우
     *
     */
    fun findUser(id: Long) : UserModel {
        return userRepository.findById(id)
            .map { modelMapper.mapper<User, UserModel>(it) }
            .orElseThrow { NotFoundDataException("해당 아이디의 사용자 정보를 찾을 수 없습니다. id : $id") }
    }

    /**
     * user 단건 조회
     *
     * @param id 회원 아이디
     * @param exception 던져질 예외
     * @return user 엔티티 조회용 모델 UserModel 반환
     * @throws exception 회원 데이터가 없는 경우, 파라미터로 넘겨진 예외가 던져집니다.
     *
     */
    fun findUser(id: Long, exception: Exception) : User {
        return userRepository.findById(id)
            .orElseThrow { exception }
    }

    /**
     * user 단건 조회
     *
     * @param email 회원 이메일
     * @param exception 던져질 예외
     * @return user 엔티티 조회용 모델 UserModel 반환
     * @throws exception 회원 데이터가 없는 경우, 파라미터로 넘겨진 예외가 던져집니다.
     *
     */
    fun findUser(email: Email, exception: Exception): User {
        return userRepository.findByEmail(email) ?: throw exception;
    }
}