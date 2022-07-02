package com.shallwecode.project.controller

import com.ninjasquad.springmockk.MockkBean
import com.shallwecode.common.exception.NotFoundDataException
import com.shallwecode.descriptor.HttpResponseDescriptors
import com.shallwecode.descriptor.PageResponseDescriptors
import com.shallwecode.project.controller.request.ProjectPagingParameters
import com.shallwecode.project.descriptor.ProjectModelDescriptors
import com.shallwecode.project.entity.*
import com.shallwecode.project.entity.model.ProjectDetailModel
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
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.context.WebApplicationContext
import java.time.LocalDateTime
import java.util.stream.Collectors
import java.util.stream.IntStream

class ProjectQueryControllerUnitTest : RestDocConfig {
    lateinit var mockMvc: MockMvc

    @MockkBean
    lateinit var projectQueryService: ProjectQueryService

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
                "get-project-list-order-by-id-desc",
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

    @Test
    fun `getProjectDetail - 프로젝트 상세 조회`() {
        // given
        val projectId = 10L

        val joinedUserList = mutableListOf(
            JoinedUser(
                id = JoinedUserId(10L),
                status = JoinedUserStatus.JOINED,
            ),
            JoinedUser(
                id = JoinedUserId(11L),
                status = JoinedUserStatus.JOINED,
            )
        )
        val techStackList = mutableListOf(
            TechStack("spring boot"),
            TechStack("kotlin")
        )

        val projectDetailModel = ProjectDetailModel(
            id = projectId,
            status = ProjectStatus.RECRUITING,
            title = "프로젝트 제목",
            description = "프로젝트 설명",
            createdUserId = 100L,
            joinedUsers = joinedUserList,
            techStacks = techStackList,
            createDateTime = LocalDateTime.now(),
            updateDateTime = LocalDateTime.now()
        )

        every { projectQueryService.getProject(projectId) } returns projectDetailModel

        // when, then
        mockMvc.perform(
            get("/projects/{projectId}", projectId)
        ).andExpectAll(
            status().isOk
        ).andDo(
            MockMvcResultHandlers.print()
        ).andDo(
            document(
                "get-project-detail",
                pathParameters(
                    parameterWithName("projectId").description("프로젝트 아이디")
                ),
                responseFields(
                    HttpResponseDescriptors.httpResponseDescriptors(
                        *ProjectModelDescriptors.projectDetailModelFieldDescriptors().toTypedArray()
                    )
                )
            )
        )
    }

    @Test
    fun `getProjectDetail - 프로젝트를 찾을 수 없는 경우`() {
        // given
        val projectId = 77L
        val errorMessage = "not found project"
        every { projectQueryService.getProject(any()) }
            .throws(NotFoundDataException(errorMessage))

        // when, then
        mockMvc.perform(
            get("/projects/{projectId}", projectId)
        ).andExpectAll(
            status().isNotFound,
            jsonPath("$.message").value(errorMessage)
        ).andDo(
            MockMvcResultHandlers.print()
        ).andDo(
            document(
                "get-project-detail-not-exists",
                pathParameters(
                    parameterWithName("projectId").description("프로젝트 아이디")
                ),
                responseFields(
                    HttpResponseDescriptors.httpErrorResponseDescriptors()
                )
            )
        )
    }

}