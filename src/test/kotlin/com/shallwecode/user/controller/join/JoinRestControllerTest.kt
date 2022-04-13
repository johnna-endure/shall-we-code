package com.shallwecode.user.controller.join

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.shallwecode.common.exception.BadRequestException
import com.shallwecode.common.http.HttpResponseDescriptors
import com.shallwecode.testconfig.MockControllerTestConfig
import com.shallwecode.testconfig.RestDocConfig
import com.shallwecode.user.controller.join.request.JoinRequest
import com.shallwecode.user.dto.UserRequestDescriptors
import com.shallwecode.user.service.join.JoinService
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.context.WebApplicationContext

class JoinRestControllerTest : RestDocConfig, MockControllerTestConfig {

    var mockMvc: MockMvc? = null

    @MockkBean
    lateinit var joinService: JoinService

    var jsonMapper: ObjectMapper = ObjectMapper()

    @BeforeEach
    fun setUp(webApplicationContext: WebApplicationContext?, restDocumentation: RestDocumentationContextProvider?) {
        mockMvc = this.restDocConfigInit(webApplicationContext, restDocumentation)
    }

    @Test
    fun `회원가입 성공`() {
        //given
        val request = JoinRequest(
            email = "test@gmail.com",
            name = "cws",
            nickname = "nickname",
            password = "password123",
            profileImage = "url",
            phoneNumber = "01011112222"
        )

        every { joinService.join(any()) } returns 1L

        //when, then
        mockMvc!!.perform(
            post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request))
        )
            .andExpect { status().isOk }
            .andDo(
                document(
                    "join-success",
                    requestFields(UserRequestDescriptors.userCreateRequestFields()),
                    responseFields(
                        HttpResponseDescriptors.httpResponseDescriptors()
                            .plus(fieldWithPath("body.id").description("가입된 사용자 아이디"))
                    )
                )
            )
    }

    @Test
    fun `회원가입 실패 - 비밀번호가 8자리 이하인 경우`() {
        //given
        val request = JoinRequest(
            email = "test@gmail.com",
            name = "cws",
            nickname = "nickname",
            password = "short",
            profileImage = "url",
            phoneNumber = "01011112222"
        )

        every { joinService.join(request) } throws BadRequestException("비밀번호는 8자리 이상이어야 합니다.")

        //when, then
        mockMvc!!.perform(
            post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request))
        )
            .andExpect { status().isOk }
            .andDo(
                document(
                    "join-failure-shortPassword",
                    requestFields(UserRequestDescriptors.userCreateRequestFields()),
                    responseFields(
                        HttpResponseDescriptors.httpErrorResponseDescriptors()
                    )
                )
            )
    }

    @Test
    fun `회원가입 실패 - 이메일 검증에 실패한 경우`() {
        //given
        val request = JoinRequest(
            email = "test",
            name = "cws",
            nickname = "nickname",
            password = "short",
            profileImage = "url",
            phoneNumber = "01011112222"
        )

        val message = "이메일이 유효하지 않습니다. email : ${request.email}"
        every { joinService.join(request) } throws BadRequestException(message)
        //when, then
        mockMvc!!.perform(
            post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request))
        )
            .andExpect { status().isOk }
            .andDo(
                document(
                    "join-failure-invalidEmail",
                    requestFields(UserRequestDescriptors.userCreateRequestFields()),
                    responseFields(
                        HttpResponseDescriptors.httpErrorResponseDescriptors()
                    )
                )
            )
    }

    @Test
    fun `회원가입 실패 - 핸드폰 번호 검증에 실패한 경우`() {
        //given
        val request = JoinRequest(
            email = "test",
            name = "cws",
            nickname = "nickname",
            password = "short",
            profileImage = "url",
            phoneNumber = "010aa123asdf2"
        )
        val message = "\"하이픈 이외의 문자는 포함될 수 없습니다. phoneNumber : ${request.phoneNumber}"
        every { joinService.join(request) } throws BadRequestException(message)

        //when, then
        mockMvc!!.perform(
            post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request))
        )
            .andExpect { status().isOk }
            .andDo(
                document(
                    "join-failure-invalidPhoneNumber",
                    requestFields(UserRequestDescriptors.userCreateRequestFields()),
                    responseFields(
                        HttpResponseDescriptors.httpErrorResponseDescriptors()
                    )
                )
            )
    }


}