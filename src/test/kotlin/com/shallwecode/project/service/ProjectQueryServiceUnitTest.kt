package com.shallwecode.project.service

import com.shallwecode.project.controller.request.ProjectPagingParameters
import com.shallwecode.project.controller.request.ProjectSortField
import com.shallwecode.project.entity.*
import com.shallwecode.project.repository.ProjectRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.util.stream.Collectors
import java.util.stream.IntStream

@DataJpaTest
class ProjectQueryServiceUnitTest {

    @MockK
    lateinit var projectRepository: ProjectRepository

    lateinit var projectQueryService: ProjectQueryService

    @BeforeEach
    fun setup() {
        projectQueryService = ProjectQueryService(projectRepository)
    }

    @Test
    fun beanLoadingTest() {
        assertThat(projectRepository).isNotNull
    }

    @Test
    fun `getProjectList - 프로젝트 ID, 내림차순 페이징 조회`() {
        // given
        val pageParameters = ProjectPagingParameters(
            page = 0,
            size = 5,
            sortField = ProjectSortField.ID,
            isAscending = false
        )
        // 테스트 데이터 초기화
        val projects = IntStream.range(0, 5)
            .mapToObj { n ->
                val project = Project(
                    status = ProjectStatus.RECRUITING,
                    title = "title${n}",
                    description = "desc${n}",
                    createdUserId = n.toLong(),
                )
                project.id.value = n.toLong()
                project
            }.collect(Collectors.toList())

        val pageResult: Page<Project> = PageImpl(projects, pageParameters.toPageable(), 20)
        every { projectRepository.findAll(any<PageRequest>()) }
            .returns(pageResult)

        // when
        val result = projectQueryService.getProjectList(pageParameters)

        // then
        assertThat(result.isFirst).isTrue
        assertThat(result.size).isEqualTo(5)
        assertThat(result.totalElements).isEqualTo(20)
        assertThat(result.number).isEqualTo(0)
        assertThat(result.sort.isSorted).isTrue

        result.forEachIndexed() { index, model ->
            val entity = projects[index]
            assertThat(model.id).isEqualTo(entity.id.value)
            assertThat(model.title).isEqualTo(entity.title)
            assertThat(model.description).isEqualTo(entity.description)
            assertThat(model.createdUserId).isEqualTo(entity.createdUserId)
            assertThat(model.createDateTime).isEqualTo(entity.createDateTime.toString())
            assertThat(model.updateDateTime).isEqualTo(entity.updateDateTime.toString())
        }
    }

    @Test
    fun `getProjectList - 조회 결과가 없는 경우`() {
        // given
        val pageParameters = ProjectPagingParameters(
            page = 0,
            size = 5,
            sortField = ProjectSortField.ID,
            isAscending = false
        )
        // 테스트 데이터 초기화
        val pageResult: Page<Project> = PageImpl(listOf(), pageParameters.toPageable(), 0)
        every { projectRepository.findAll(any<PageRequest>()) }
            .returns(pageResult)

        // when
        val result = projectQueryService.getProjectList(pageParameters)

        // then
        assertThat(result.size).isEqualTo(5)
        assertThat(result.number).isEqualTo(0)
        assertThat(result.totalElements).isEqualTo(0)
        assertThat(result.sort.isSorted).isTrue
        assertThat(result.isFirst).isTrue
        assertThat(result.isLast).isTrue
        assertThat(result.totalPages).isEqualTo(0)
        assertThat(result.isEmpty).isTrue
    }

    // TODO 어떻게 조회할건지 더 생각 필요
    @Test
    fun `getProject - 참여 유저, 기술 스택 정보가 모두 있는 경우 조회`() {
        // given
        val givenProject = Project(
            status = ProjectStatus.RECRUITING,
            title = "title",
            description = "description",
            createdUserId = 11L,
            joinedUsers = mutableListOf(
                JoinedUser(
                    id = JoinedUserId(100L),
                    status = JoinedUserStatus.JOINED
                ),
                JoinedUser(
                    id = JoinedUserId(105L),
                    status = JoinedUserStatus.JOINED
                )
            ),
            techStacks = mutableListOf(
                TechStack("spring boot"),
                TechStack("kotlin"),
            )
        )

        // when
//        projectQueryService.getProject()

        // then
    }
}