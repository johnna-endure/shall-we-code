package com.shallwecode.project.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.shallwecode.descriptor.PageResponseDescriptors
import com.shallwecode.project.controller.request.ProjectPagingParameters
import com.shallwecode.project.controller.request.ProjectSortField
import com.shallwecode.project.descriptor.ProjectModelDescriptors
import com.shallwecode.project.entity.ProjectStatus
import com.shallwecode.project.entity.model.ProjectListItemModel
import com.shallwecode.project.service.ProjectQueryService
import com.shallwecode.testconfig.RestDocConfig
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageImpl
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.requestParameters
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.context.WebApplicationContext
import java.time.LocalDateTime
import java.util.stream.Collectors
import java.util.stream.IntStream

class ProjectQueryControllerUnitTest : RestDocConfig {
    lateinit var mockMvc: MockMvc

    @MockkBean
    lateinit var projectQueryService: ProjectQueryService

    var jsonMapper: ObjectMapper = ObjectMapper()

    @BeforeEach
    fun setUp(webApplicationContext: WebApplicationContext?, restDocumentation: RestDocumentationContextProvider?) {
        mockMvc = this.restDocConfigInit(webApplicationContext, restDocumentation)
    }

    @Test
    fun `getProjects - 프로젝트 리스트 조회`() {
        // given
        val pageParameters = ProjectPagingParameters(
            page = 0,
            size = 5,
            sortField = ProjectSortField.ID,
            isAscending = false
        )
        val projectModels = IntStream.range(0, 5)
            .mapToObj { n ->
                val model = ProjectListItemModel(
                    id = n.toLong() + 200,
                    status = ProjectStatus.PROGRESS,
                    title = "title${n}",
                    description = "desc${n}",
                    createdUserId = n.toLong() * 10,
                    createDateTime = LocalDateTime.now(),
                    updateDateTime = LocalDateTime.now()
                )
                model
            }
            .collect(Collectors.toList())

        every { projectQueryService.getProjectList(pageParameters) }
            .returns(PageImpl(projectModels, pageParameters.toPageable(), 50))

        // when, then
        mockMvc.perform(
            get("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", pageParameters.page.toString())
                .param("size", pageParameters.size.toString())
                .param("sortField", pageParameters.sortField!!.name)
                .param("isAscending", pageParameters.isAscending.toString())
        ).andExpectAll(
            status().isOk
        ).andDo(
            MockMvcResultHandlers.print()
        ).andDo(
            document(
                "project-list-order-by-id-desc",
                requestParameters(
                    parameterWithName("page").description("페이지 번호"),
                    parameterWithName("size").description("페이지 사이즈"),
                    parameterWithName("sortField").description("정렬 대상 필드 이름[ID]"),
                    parameterWithName("isAscending").description("오름차순 = true, 내림차순 = false"),
                ),
                responseFields(
                    PageResponseDescriptors.pagingResponseDescriptors(
                        modelDescriptors = ProjectModelDescriptors.projectListItemModelFieldDescriptors()
                    )
                )
            )
        )

    }

}