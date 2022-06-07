package com.shallwecode.project.descriptor

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath

class ProjectRequestDescriptors {
    companion object {
        fun createRequestFields(): List<FieldDescriptor> {
            return listOf(
                fieldWithPath("title").description("프로젝트 제목"),
                fieldWithPath("description").description("프로젝트 설명"),
                fieldWithPath("createdUserId").optional().description("프로젝트 생성자 아이디"),
                fieldWithPath("githubUrl").optional().description("githubUrl"),
            )
        }
    }
}