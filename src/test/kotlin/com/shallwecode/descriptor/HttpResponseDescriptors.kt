package com.shallwecode.descriptor

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath

class HttpResponseDescriptors {
    companion object {
        /**
         * @param bodyDescriptor 추가적으로 필요한 응답 body의 FieldDescriptor
         */
        fun httpResponseDescriptors(vararg bodyDescriptor: FieldDescriptor): List<FieldDescriptor> {
            return listOf(
                fieldWithPath("message").description("응답 메세지"),
                fieldWithPath("body").optional().description("응답 바디"),
                *bodyDescriptor
            )
        }

        /**
         * @param bodyDescriptor 추가적으로 필요한 응답 body의 FieldDescriptor
         */
        fun httpErrorResponseDescriptors(vararg bodyDescriptor: FieldDescriptor): List<FieldDescriptor> {
            return listOf(
                fieldWithPath("message").description("에러 메세지"),
                fieldWithPath("body").optional().description("에러인 경우 nullable"),
                *bodyDescriptor
            )
        }
    }
}