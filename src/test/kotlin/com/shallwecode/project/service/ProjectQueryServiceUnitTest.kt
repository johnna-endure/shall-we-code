package com.shallwecode.project.service

import com.shallwecode.project.entity.Project
import com.shallwecode.project.entity.ProjectStatus
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
import org.springframework.data.domain.Sort
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
    fun `getProjectList - 조회에 성공하는 경우`() {
        // given
        val page = 0
        val size = 5
        // 테스트 데이터 초기화
        val projects = IntStream.range(0, 5)
            .mapToObj { n ->
                val project = Project(
                    status = ProjectStatus.RECRUITING,
                    title = "title${n}",
                    description = "desc${n}",
                    createdUserId = n.toLong(),
                )
                project._id = n.toLong()
                project
            }.collect(Collectors.toList())

        val pageRequest = PageRequest.of(page, size, Sort.by("_id").descending())
        val pageResult: Page<Project> = PageImpl(projects, pageRequest, 20)
        every { projectRepository.findAll(any<PageRequest>()) }
            .returns(pageResult)

        // when
        val result = projectQueryService.getProjectList(page, size)

        // then
        assertThat(result.isFirst).isTrue
        assertThat(result.size).isEqualTo(5)
        assertThat(result.totalElements).isEqualTo(20)
        assertThat(result.number).isEqualTo(0)
        assertThat(result.sort.isSorted).isTrue

        result.forEachIndexed() { index, model ->
            val entity = projects[index]
            assertThat(model.id).isEqualTo(entity.id)
            assertThat(model.title).isEqualTo(entity.title)
            assertThat(model.description).isEqualTo(entity.description)
            assertThat(model.createdUserId).isEqualTo(entity.createdUserId)
            assertThat(model.createDateTime).isEqualTo(entity.createDateTime.toString())
            assertThat(model.updateDateTime).isEqualTo(entity.updateDateTime.toString())
        }
    }

}