package com.shallwecode.user.controller.join

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.shallwecode.common.exception.BadRequestException
import com.shallwecode.descriptor.HttpResponseDescriptors
import com.shallwecode.testconfig.RestDocConfig
import com.shallwecode.user.controller.join.request.DuplicateCheckRequest
import com.shallwecode.user.controller.join.request.UserJoinRequest
import com.shallwecode.user.descriptor.UserRequestDescriptors
import com.shallwecode.user.service.UserJoinService
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.context.WebApplicationContext

class UserJoinControllerTest : RestDocConfig {
    lateinit var mockMvc: MockMvc

    @MockkBean
    lateinit var userJoinService: UserJoinService

    var jsonMapper: ObjectMapper = ObjectMapper()

    @BeforeEach
    fun setUp(webApplicationContext: WebApplicationContext?, restDocumentation: RestDocumentationContextProvider?) {
        mockMvc = this.restDocConfigInit(webApplicationContext, restDocumentation)
    }

    //
    @Test
    fun `회원가입 성공`() {
        //given
        val request = UserJoinRequest(
            email = "test@gmail.com",
            name = "cws",
            nickname = "nickname",
            password = "password123",
            profileImage = "url",
            phoneNumber = "01011112222"
        )

        every { userJoinService.join(any()) } returns 1L

        //when, then
        mockMvc.perform(
            post("/user/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request))
        )
            .andExpect { status().isOk }
            .andDo(
                document(
                    "join-success",
                    requestFields(*UserRequestDescriptors.userCreateRequestFields().toTypedArray()),
                    responseFields(
                        *HttpResponseDescriptors.httpResponseDescriptors()
                            .plus(fieldWithPath("body.id").description("가입된 사용자 아이디")).toTypedArray()
                    )
                )
            )
    }

    @Test
    fun `회원가입 실패 - 비밀번호가 8자리 이하인 경우`() {
        //given
        val request = UserJoinRequest(
            email = "test@gmail.com",
            name = "cws",
            nickname = "nickname",
            password = "short",
            profileImage = "url",
            phoneNumber = "01011112222"
        )

        every { userJoinService.join(request) } throws BadRequestException("비밀번호는 8자리 이상이어야 합니다.")

        //when, then
        mockMvc.perform(
            post("/user/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request))
        )
            .andExpect { status().isOk }
            .andDo(
                document(
                    "join-failure-shortPassword",
                    requestFields(*UserRequestDescriptors.userCreateRequestFields().toTypedArray()),
                    responseFields(
                        *HttpResponseDescriptors.httpErrorResponseDescriptors().toTypedArray()
                    )
                )
            )
    }

    @Test
    fun `회원가입 실패 - 이메일 검증에 실패한 경우`() {
        //given
        val request = UserJoinRequest(
            email = "test",
            name = "cws",
            nickname = "nickname",
            password = "short",
            profileImage = "url",
            phoneNumber = "01011112222"
        )

        val message = "이메일이 유효하지 않습니다. email : ${request.email}"
        every { userJoinService.join(request) } throws BadRequestException(message)
        //when, then
        mockMvc.perform(
            post("/user/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request))
        )
            .andExpect { status().isOk }
            .andDo(
                document(
                    "join-failure-invalidEmail",
                    requestFields(*UserRequestDescriptors.userCreateRequestFields().toTypedArray()),
                    responseFields(
                        *HttpResponseDescriptors.httpErrorResponseDescriptors().toTypedArray()
                    )
                )
            )
    }

    @Test
    fun `회원가입 실패 - 핸드폰 번호 검증에 실패한 경우`() {
        //given
        val request = UserJoinRequest(
            email = "test",
            name = "cws",
            nickname = "nickname",
            password = "short",
            profileImage = "url",
            phoneNumber = "010aa123asdf2"
        )
        val message = "\"하이픈 이외의 문자는 포함될 수 없습니다. phoneNumber : ${request.phoneNumber}"
        every { userJoinService.join(request) } throws BadRequestException(message)

        //when, then
        mockMvc.perform(
            post("/user/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request))
        )
            .andExpect { status().isOk }
            .andDo(
                document(
                    "join-failure-invalidPhoneNumber",
                    requestFields(*UserRequestDescriptors.userCreateRequestFields().toTypedArray()),
                    responseFields(
                        *HttpResponseDescriptors.httpErrorResponseDescriptors().toTypedArray()
                    )
                )
            )
    }

    @Test
    fun `duplicateCheck - 이메일이 중복이 아닌 경우`() {
        // given
        val email = "test@gmail.com"
        val request = DuplicateCheckRequest(email)
        every { userJoinService.duplicateEmailCheck(any()) } returns Pair("duplicated", false)

        mockMvc.perform(
            post("/user/join/duplicate-check")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsBytes(request))
        )
            .andExpectAll(
                status().isOk,
                jsonPath("$.body.duplicated").value(false)
            )
            .andDo(
                document(
                    "join-email-check:중복이 아닌 경우",
                    requestFields(
                        fieldWithPath("email").description("사용자가 입력한 이메일")
                    ),
                    responseFields(
                        *HttpResponseDescriptors.httpResponseDescriptors(
                            fieldWithPath("body.duplicated").description("이메일 중복 여부")
                        ).toTypedArray()
                    )
                )
            )
    }

    @Test
    fun `duplicateCheck - 이메일이 중복인 경우`() {
        // given
        val email = "test@gmail.com"
        val request = DuplicateCheckRequest(email)
        every { userJoinService.duplicateEmailCheck(any()) } returns Pair("duplicated", true)

        mockMvc.perform(
            post("/user/join/duplicate-check")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsBytes(request))
        )
            .andExpectAll(
                status().isOk,
                jsonPath("$.body.duplicated").value(true)
            )
            .andDo(
                document(
                    "join-email-check:중복인 경우",
                    requestFields(
                        fieldWithPath("email").description("사용자가 입력한 이메일")
                    ),
                    responseFields(
                        *HttpResponseDescriptors.httpResponseDescriptors(
                            fieldWithPath("body.duplicated").description("이메일 중복 여부")
                        ).toTypedArray()
                    )
                )
            )
    }

    @Test
    fun `duplicateCheck - 에러로 실패하는 경우`() {
        // given
        val email = "test@gmail.com"
        val request = DuplicateCheckRequest(email)
        val errorMessage = "test error"
        every { userJoinService.duplicateEmailCheck(any()) } throws RuntimeException(errorMessage)

        mockMvc.perform(
            post("/user/join/duplicate-check")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsBytes(request))
        )
            .andExpectAll(
                status().is5xxServerError,
                jsonPath("$.message").value(errorMessage)
            )
            .andDo(
                document(
                    "join-email-check:에러 발생",
                    requestFields(
                        fieldWithPath("email").description("사용자가 입력한 이메일")
                    ),
                    responseFields(
                        *HttpResponseDescriptors.httpErrorResponseDescriptors().toTypedArray()
                    )
                )
            )
    }

}