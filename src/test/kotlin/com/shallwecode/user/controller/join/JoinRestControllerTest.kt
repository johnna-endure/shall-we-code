package com.shallwecode.user.controller.join

import com.fasterxml.jackson.databind.ObjectMapper
import com.shallwecode.common.http.HttpResponseDescriptors
import com.shallwecode.testconfig.MockControllerTestConfig
import com.shallwecode.testconfig.RestDocConfig
import com.shallwecode.user.dto.UserResponseDescriptors
import com.shallwecode.user.dto.UserRequestDescriptors
import com.shallwecode.user.dto.request.UserCreateRequest
import com.shallwecode.user.service.join.JoinService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
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

class JoinRestControllerTest : RestDocConfig, MockControllerTestConfig {

    @Autowired
    var mockMvc: MockMvc? = null

    @MockBean
    var joinService: JoinService? = null

    var jsonMapper: ObjectMapper = ObjectMapper()

    @BeforeEach
    fun setUp(webApplicationContext: WebApplicationContext?, restDocumentation: RestDocumentationContextProvider?) {
        mockMvc = this.restDocConfigInit(webApplicationContext, restDocumentation)
    }

    @Test
    fun `회원가입 성공`() {
        //given
        val request = UserCreateRequest(
            email = "test@gmail.com",
            name = "cws",
            nickname = "nickname",
            password = "password123",
            profileImage = "url",
            phoneNumber = "01011112222"
        )

        `when`(joinService!!.join(request)).thenReturn(1L)

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

}