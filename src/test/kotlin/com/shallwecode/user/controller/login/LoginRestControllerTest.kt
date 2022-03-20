package com.shallwecode.user.controller.login

import com.fasterxml.jackson.databind.ObjectMapper
import com.shallwecode.common.http.HttpResponseDescriptors
import com.shallwecode.testconfig.MockControllerTestConfig
import com.shallwecode.testconfig.RestDocConfig
import com.shallwecode.user.dto.request.LoginRequest
import com.shallwecode.user.exception.login.LoginFailedException
import com.shallwecode.user.service.LoginService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.context.WebApplicationContext

class LoginRestControllerTest : RestDocConfig, MockControllerTestConfig {

    @Autowired
    var mockMvc: MockMvc? = null

    @MockBean
    var loginService: LoginService? = null

    var jsonMapper: ObjectMapper = ObjectMapper()

    @BeforeEach
    fun setUp(webApplicationContext: WebApplicationContext?, restDocumentation: RestDocumentationContextProvider?) {
        mockMvc = this.restDocConfigInit(webApplicationContext, restDocumentation)
    }

    @Test
    fun `로그인 성공`() {
        //given
        val request = LoginRequest(
            email = "test@gmail",
            password = "password"
        )

        doNothing().`when`(loginService!!).login(request)

        //when,then
        mockMvc!!.perform(
            post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request))
        )
            .andExpect { status().isOk }
            .andDo(
                document(
                    "login-success",
                    requestFields(
                        fieldWithPath("email").description("이메일"),
                        fieldWithPath("password").description("비밀번호")
                    ),
                    responseFields(
                        HttpResponseDescriptors.httpResponseDescriptors()
                    )
                )
            )
    }

    @Test
    fun `로그인 실패 - 아이디가 존재하지 않는 경우`() {
        //given
        val request = LoginRequest(
            email = "wrongEmail",
            password = "password"
        )

        `when`(loginService!!.login(request))
            .thenThrow(LoginFailedException("이메일이 ${request.email}인 사용자가 존재하지 않습니다."))

        //when,then
        mockMvc!!.perform(
            post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request))
        )
            .andExpect { status().isOk }
            .andDo(
                document(
                    "login-failure-wrongId",
                    requestFields(
                        fieldWithPath("email").description("이메일"),
                        fieldWithPath("password").description("비밀번호")
                    ),
                    responseFields(
                        HttpResponseDescriptors.httpErrorResponseDescriptors()
                    )
                )
            )
    }

    @Test
    fun `로그인 실패 - 비밀번호가 일치하지 않는 경우`() {
        //given
        val request = LoginRequest(
            email = "test@gmail.com",
            password = "wrongPassword"
        )

        `when`(loginService!!.login(request))
            .thenThrow(LoginFailedException("비밀번호가 일치하지 않습니다."))

        //when,then
        mockMvc!!.perform(
            post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request))
        )
            .andExpect { status().isOk }
            .andDo(
                document(
                    "login-failure-wrongPassword",
                    requestFields(
                        fieldWithPath("email").description("이메일"),
                        fieldWithPath("password").description("비밀번호")
                    ),
                    responseFields(
                        HttpResponseDescriptors.httpErrorResponseDescriptors()
                    )
                )
            )
    }

}