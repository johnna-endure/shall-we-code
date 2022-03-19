package com.shallwecode.user.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.shallwecode.common.exception.BadRequestException
import com.shallwecode.common.exception.NotFoundDataException
import com.shallwecode.common.http.HttpResponseDescriptors
import com.shallwecode.user.dto.UserModelDescriptors
import com.shallwecode.user.dto.UserRequestDescriptors
import com.shallwecode.user.dto.model.UserModel
import com.shallwecode.user.dto.request.UserCreateRequest
import com.shallwecode.user.entity.embeddable.Email
import com.shallwecode.user.service.UserService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.`when`
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
@AutoConfigureMockMvc
//@Transactional
@SpringBootTest
@ExtendWith(MockitoExtension::class)
class UserRestControllerTest {

    @MockBean
    var userService: UserService? = null;

    @Autowired
    var mockMvc: MockMvc? = null

    val objectMapper = ObjectMapper()

    @BeforeEach
    fun setUp(webApplicationContext: WebApplicationContext?, restDocumentation: RestDocumentationContextProvider?) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext!!)
            .apply<DefaultMockMvcBuilder>(
                documentationConfiguration(restDocumentation)
                    .operationPreprocessors()
                    .withResponseDefaults(prettyPrint())
                    .withRequestDefaults(prettyPrint())
            )
            .build()
    }


    @Test
    fun `사용자 생성 성공`() {
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

        `when`(userService!!.createUser(request)).thenReturn(1L)

        //when, then
        mockMvc!!.perform(
            post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect { status().isOk }
            .andDo(
                document(
                    "create-user-success",
                    requestFields(UserRequestDescriptors.userCreateRequestFields()),
                    responseFields(
                        fieldWithPath("status").description("http 상태코드"),
                        fieldWithPath("message").description("응답 메세지"),
                        fieldWithPath("body").optional().description("응답 내용"),
                        fieldWithPath("body.id").description("생성된 사용자 아이디"),
                    )
                )
            )
    }


    @Test
    fun `사용자 생성 실패 - 이메일 유효성 검증에 실패한 경우 BadRequestException 예외처리`() {
        //given
        val request = UserCreateRequest(
            email = "invalidEmail", //이메일
            name = "name", // 사용자 이름
            nickname = "nickname", // 닉네임
            password = "password", // 비밀번호
            phoneNumber = "01065688036", // 핸드폰 번호
            profileImage = null, // 프로필 사진 url
            githubUrl = null, // 깃허브 url
            blogUrl = null // 개인 블로그 url
        )
        `when`(userService!!.createUser(request)).thenThrow(BadRequestException("이메일이 유효하지 않습니다. email : ${request.email}"))

        //when, then
        mockMvc!!.perform(
            post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpectAll(
                status().isBadRequest,
                jsonPath("$.message").value("이메일이 유효하지 않습니다. email : invalidEmail")
            )
            .andDo(
                document(
                    "create-user-failure-badReqeust",
                    requestFields(UserRequestDescriptors.userCreateRequestFields()),
                    responseFields(
                        fieldWithPath("status").description("http 상태코드"),
                        fieldWithPath("message").description("에러 메세지"),
                        fieldWithPath("body").optional().description("응답 바디. 에러 응답의 경우 null"),
                    )
                )
            )
    }


    @Test
    fun `사용자 정보 단건 조회 성공`() {
        //given
        val id = 1L
        val userModel = UserModel(
            id = id,
            email = Email("test@gmail.com"),
            name = "cws",
            nickname = "right stone",
            phoneNumber = "01065688036",
            profileImage = "url",
            githubUrl = "url",
            blogUrl = "url"
        )

        `when`(userService!!.getUser(id)).thenReturn(userModel)

        mockMvc!!.perform(get("/users/{id}", id))
            .andExpect(status().isOk)
            .andDo(
                document(
                    "get-user-success",
                    pathParameters(
                        parameterWithName("id").description("사용자 아이디")
                    ),
                    responseFields(
                        HttpResponseDescriptors.httpResponseDescriptors()
                            .plus(UserModelDescriptors.userModelFields("body"))
                    )
                )
            )
    }

    @Test
    fun `사용자 정보 단건 조회 실패 - 사용자 데이터가 존재하지 않는 경우`() {
        //given
        val id = 1L

        `when`(userService!!.getUser(id)).thenThrow(NotFoundDataException("해당 사용자를 찾을 수 없습니다."))

        mockMvc!!.perform(get("/users/{id}", id))
            .andExpect(status().isNotFound)
            .andDo(
                document(
                    "get-user-failure-notfound",
                    pathParameters(
                        parameterWithName("id").description("사용자 아이디")
                    ),
                    responseFields(
                        HttpResponseDescriptors.httpErrorResponseDescriptors()
                    )
                )
            )
    }


}