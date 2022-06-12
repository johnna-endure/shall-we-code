package com.shallwecode.descriptor

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath

class PageResponseDescriptors {
    companion object {
        /**
         * @param modelDescriptors 추가적으로 필요한 응답 body의 FieldDescriptor
         */
        fun pagingResponseDescriptors(
            pagingFieldPrefix: String = "body",
            modelDescriptors: Array<FieldDescriptor>
        ): List<FieldDescriptor> {
            return listOf(
                fieldWithPath("message").description("응답 메세지"),

                fieldWithPath("${pagingFieldPrefix}.first").description("첫 페이지 여부"),
                fieldWithPath("${pagingFieldPrefix}.last").description("마지막 페이지 여부"),
                fieldWithPath("${pagingFieldPrefix}.empty").description("페이지에 요소가 없는지 여부"),
                fieldWithPath("${pagingFieldPrefix}.totalPages").description("총 페이지 수"),
                fieldWithPath("${pagingFieldPrefix}.totalElements").description("총 페이징 요소 개수"),
                fieldWithPath("${pagingFieldPrefix}.number").description("페이지 번호"),
                fieldWithPath("${pagingFieldPrefix}.size").description("페이지 크기"),
                fieldWithPath("${pagingFieldPrefix}.numberOfElements").description("현재 페이지 요소 개수"),
                fieldWithPath("${pagingFieldPrefix}.pageable.*").ignored(),
                fieldWithPath("${pagingFieldPrefix}.pageable.sort.*").ignored(),
                fieldWithPath("${pagingFieldPrefix}.sort.*").ignored(),
                *modelDescriptors
            )
        }

    }

}