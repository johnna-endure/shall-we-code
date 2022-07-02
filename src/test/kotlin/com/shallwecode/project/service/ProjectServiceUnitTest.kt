package com.shallwecode.project.service

import com.shallwecode.project.controller.request.ProjectCreateRequest
import com.shallwecode.project.repository.ProjectRepository
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ProjectServiceUnitTest {

    lateinit var projectService: ProjectService
    lateinit var projectRepository: ProjectRepository

    @BeforeEach
    fun beforeEach() {
        projectRepository = mockk()
        projectService = ProjectService(projectRepository)
    }

    @Test
    fun mockLoadingTest() {
        assertThat(projectRepository).isNotNull
    }

    @Test
    fun `createProject - 프로젝트 생성 성공하는 경우`() {
        // given
        val techStacks = listOf("spring boot", "kotlin")
        val creatorId = 1L
        val request = ProjectCreateRequest(
            title = "title",
            description = "description",
            githubUrl = "url",
            techStacks = techStacks
        )

        val savedProject = request.toEntity(creatorId)
        savedProject.id = 10L

        every { projectRepository.save(any()) }
            .returns(savedProject)

        // when
        val id = projectService.createProject(request, creatorId)

        // then
        assertThat(id).isEqualTo(savedProject.id)
    }

}