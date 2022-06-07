package com.shallwecode.project.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.shallwecode.common.http.HttpResponseDescriptors
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.context.WebApplicationContext

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
            githubUrl = githubUrl
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
                requestFields(ProjectRequestDescriptors.createRequestFields()),
                responseFields(
                    HttpResponseDescriptors.httpResponseDescriptors(
                        fieldWithPath("body.id").description("생성된 프로젝트 아이디")
                    )
                )
            )
        )
    }
}