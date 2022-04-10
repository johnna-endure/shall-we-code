package com.shallwecode.common.http

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.payload.PayloadDocumentation.*

class HttpResponseDescriptors {
    companion object {
        fun httpResponseDescriptors(): List<FieldDescriptor>{
            return listOf(
                fieldWithPath("message").description("응답 메세지"),
                fieldWithPath("body").optional().description("응답 바디")
            )
        }

        fun httpErrorResponseDescriptors(): List<FieldDescriptor>{
            return listOf(
                fieldWithPath("message").description("에러 메세지"),
                fieldWithPath("body").optional().description("에러인 경우 nullable")
            )
        }
    }
}