package com.shallwecode.project.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.shallwecode.descriptor.HttpResponseDescriptors
import com.shallwecode.project.controller.request.ProjectCreateRequest
import com.shallwecode.project.descriptor.ProjectRequestDescriptors
import com.shallwecode.project.service.ProjectService
import com.shallwecode.testconfig.RestDocConfig
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.context.WebApplicationContext
import java.io.IOException

class ProjectControllerUnitTest : RestDocConfig {
    lateinit var mockMvc: MockMvc

    @MockkBean
    lateinit var projectService: ProjectService

    var jsonMapper: ObjectMapper = ObjectMapper()

    @BeforeEach
    fun setUp(webApplicationContext: WebApplicationContext?, restDocumentation: RestDocumentationContextProvider?) {
        mockMvc = this.restDocConfigInit(webApplicationContext, restDocumentation)
    }

    @Test
    fun `createProject - 프로젝트 생성 성공`() {
        // given
        val title = "title"
        val description = "desc"
        val createdUserId = 1L
        val githubUrl = "url"

        val request = ProjectCreateRequest(
            title = title,
            description = description,
            createdUserId = createdUserId,
            githubUrl = githubUrl,
            techStacks = listOf("spring boot", "kotlin")
        )
        every { projectService.createProject(request) } returns 10L

        // when, then
        mockMvc.perform(
            post("/project")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request))
        ).andExpect(
            status().isOk
        ).andDo(
            document(
                "project-create-success",
                requestFields(*ProjectRequestDescriptors.createRequestFields().toTypedArray()),
                responseFields(
                    *HttpResponseDescriptors.httpResponseDescriptors(
                        fieldWithPath("body.id").description("생성된 프로젝트 아이디")
                    ).toTypedArray()
                )
            )
        )
    }

    @Test
    fun `createProject - 서비스 계층에서 IOException을 던지는 경우`() {
        // given
        val title = "title"
        val description = "desc"
        val createdUserId = 1L
        val githubUrl = "url"

        val request = ProjectCreateRequest(
            title = title,
            description = description,
            createdUserId = createdUserId,
            githubUrl = githubUrl,
            techStacks = listOf("spring boot", "kotlin")
        )
        val errorMessage = "test error"
        every { projectService.createProject(request) } throws IOException(errorMessage)

        // when, then
        mockMvc.perform(
            post("/project")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request))
        ).andExpectAll(
            status().is5xxServerError,
            jsonPath("$.message").value(errorMessage)
        ).andDo(
            document(
                "project-create-failure",
                requestFields(*ProjectRequestDescriptors.createRequestFields().toTypedArray()),
                responseFields(
                    *HttpResponseDescriptors.httpErrorResponseDescriptors().toTypedArray()
                )
            )
        ).andDo(print())
    }

}