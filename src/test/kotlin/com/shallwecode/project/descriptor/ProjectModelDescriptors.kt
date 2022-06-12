package com.shallwecode.project.descriptor

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath

class ProjectModelDescriptors {
    companion object {
        fun projectListItemModelFieldDescriptors(prefix: String = "body.content[]"): Array<FieldDescriptor> {
            return arrayOf(
                fieldWithPath("${prefix}.id").description("프로젝트 ID"),
                fieldWithPath("${prefix}.status").description("프로젝트 상태"),
                fieldWithPath("${prefix}.title").description("프로젝트 제목"),
                fieldWithPath("${prefix}.description").description("프로젝트 설명"),
                fieldWithPath("${prefix}.createdUserId").description("프로젝트를 생성한 사용자 아이디"),
                fieldWithPath("${prefix}.githubUrl").description("프로젝트 깃허브 url"),
                fieldWithPath("${prefix}.createDateTime").description("프로젝트 생성 날짜"),
                fieldWithPath("${prefix}.updateDateTime").description("프로젝트 수정 날짜"),
            )
        }
    }
}