package com.shallwecode.user.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.shallwecode.user.dto.request.UserCreateRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.WebApplicationContext


@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
@Transactional
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    var mockMvc: MockMvc? = null

    val objectMapper = ObjectMapper()

    @BeforeEach
    fun setUp(webApplicationContext: WebApplicationContext?, restDocumentation: RestDocumentationContextProvider?) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext!!)
            .apply<DefaultMockMvcBuilder>(documentationConfiguration(restDocumentation)
                .operationPreprocessors()
                .withResponseDefaults(prettyPrint())
                .withRequestDefaults(prettyPrint()))
            .build()
    }


    @Test
    fun `유저 생성 성공`() {
        //given
        val request = UserCreateRequest(
            email = "test@gmail.com", //이메일
            name = "name", // 사용자 이름
            nickname = "nickname", // 닉네임
            password = "password", // 비밀번호
            phoneNumber = "01065688036", // 핸드폰 번호
            profileImage = null, // 프로필 사진 url
            githubUrl = null, // 깃허브 url
            blogUrl = null // 개인 블로그 url
        )


        //when, then
        mockMvc!!.perform(
            post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect { status().isOk }
            .andDo(document("create-user",
                requestFields(
                    fieldWithPath("email").description("이메일"),
                    fieldWithPath("name").description("이름"),
                    fieldWithPath("nickname").optional().description("닉네임"),
                    fieldWithPath("password").description("비밀번호"),
                    fieldWithPath("phoneNumber").description("핸드폰 번호"),
                    fieldWithPath("profileImage").optional().description("프로필 이미지 url"),
                    fieldWithPath("githubUrl").optional().description("깃허브 url"),
                    fieldWithPath("blogUrl").optional().description("블로그 url"),
                ),
                responseFields()
            ))

    }

}