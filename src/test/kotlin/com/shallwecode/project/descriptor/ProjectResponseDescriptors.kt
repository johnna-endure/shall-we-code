package com.shallwecode.project.descriptor

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation

class ProjectResponseDescriptors {
    companion object {
        fun createResponseFields(): List<FieldDescriptor> {
            return listOf(
                PayloadDocumentation.fieldWithPath("title").description("프로젝트 제목"),
                PayloadDocumentation.fieldWithPath("description").description("프로젝트 설명"),
                PayloadDocumentation.fieldWithPath("createdUserId").optional().description("프로젝트 생성자 아이디"),
                PayloadDocumentation.fieldWithPath("githubUrl").optional().description("githubUrl"),
            )
        }
    }
}