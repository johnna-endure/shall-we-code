package com.shallwecode.user.service

import com.shallwecode.common.exception.NotFoundDataException
import com.shallwecode.common.util.modelmapper.ModelMapper
import com.shallwecode.user.dto.model.UserModel
import com.shallwecode.user.dto.request.UserCreateRequest
import com.shallwecode.user.entity.User
import com.shallwecode.user.entity.embeddable.Email
import com.shallwecode.user.entity.embeddable.Password
import com.shallwecode.user.repository.UserRepository
import org.springframework.stereotype.Service

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
    fun createUser(request: UserCreateRequest): Long {
        val user = User(
            Email(request.email),
            request.name,
            request.nickname,
            Password(request.password),
            request.phoneNumber,
            request.profileImage,
            request.githubUrl,
            request.blogUrl,
            false
        )
        return userRepository.save(user).id
    }

    /**
     * user 단건 조회
     *
     * @param id 회원 아이디
     * @return user 엔티티 조회용 모델 UserModel 반환
     * @throws NoSuchElementException 회원 데이터가 없는 경우
     *
     */
    fun getUser(id: Long) : UserModel {
        return userRepository.findById(id)
            .map { modelMapper.mapper<User, UserModel>(it) }
            .orElseThrow { NotFoundDataException("해당 아이디의 사용자 정보를 찾을 수 없습니다. id : $id") }
    }
}