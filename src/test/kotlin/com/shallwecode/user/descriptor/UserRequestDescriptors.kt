package com.shallwecode.user.descriptor

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath

class UserRequestDescriptors {

    companion object {
        /**
         * user 생성시 요청 객체 필드 정보들
         */
        fun userCreateRequestFields(): Array<FieldDescriptor> {
            return arrayOf(
                fieldWithPath("email").description("이메일"),
                fieldWithPath("name").description("이름"),
                fieldWithPath("nickname").optional().description("닉네임"),
                fieldWithPath("password").description("비밀번호"),
                fieldWithPath("phoneNumber").description("핸드폰 번호"),
                fieldWithPath("profileImage").optional().description("프로필 이미지 url"),
                fieldWithPath("githubUrl").optional().description("깃허브 url"),
                fieldWithPath("blogUrl").optional().description("블로그 url"),
            )
        }
    }

}