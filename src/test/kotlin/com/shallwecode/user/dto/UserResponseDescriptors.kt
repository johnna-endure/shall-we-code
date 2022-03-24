package com.shallwecode.user.dto

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation

class UserResponseDescriptors {

    companion object {
        fun userModelFields(path: String = ""): List<FieldDescriptor> {
            val prefix = if(path == "") "" else "${path}."
            return listOf(
                PayloadDocumentation.fieldWithPath("${prefix}id").description("아이디"),
                PayloadDocumentation.fieldWithPath("${prefix}email.email").description("이메일"),
                PayloadDocumentation.fieldWithPath("${prefix}name").description("이름"),
                PayloadDocumentation.fieldWithPath("${prefix}nickname").description("닉네임"),
                PayloadDocumentation.fieldWithPath("${prefix}phoneNumber.phoneNumber").description("핸드폰 번호"),
                PayloadDocumentation.fieldWithPath("${prefix}profileImage").description("프로필 이미지"),
                PayloadDocumentation.fieldWithPath("${prefix}githubUrl").description("깃허브 url"),
                PayloadDocumentation.fieldWithPath("${prefix}blogUrl").description("블로그 url"),
            )
        }
    }
}