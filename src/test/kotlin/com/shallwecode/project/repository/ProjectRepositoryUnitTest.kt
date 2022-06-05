package com.shallwecode.project.repository

import com.shallwecode.project.entity.Project
import com.shallwecode.project.entity.ProjectStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class ProjectRepositoryUnitTest(
    @Autowired
    val projectRepository: ProjectRepository
) {

    @Test
    fun beanLoadingTest() {
        assertThat(projectRepository).isNotNull
    }

    @Test
    fun `save - 참여 유저가 없는 프로젝트 엔티티 저장`() {
        // given
        val expected = Project(
            status = ProjectStatus.RECRUITING,
            title = "title",
            description = "description",
            createdUserId = 1L,
            githubUrl = "githubUrl"
        )

        // when
        var saved = projectRepository.saveAndFlush(expected)

        // then
        saved = projectRepository.findById(saved.id).get()
        assertThat(saved.id).isNotNull
        assertThat(saved.id).isEqualTo(expected.id)
        assertThat(saved.title).isEqualTo(expected.title)
        assertThat(saved.description).isEqualTo(expected.description)
        assertThat(saved.createdUserId).isEqualTo(expected.createdUserId)
        assertThat(saved.githubUrl).isEqualTo(expected.githubUrl)
        assertThat(saved.joinedUsers).hasSameElementsAs(expected.joinedUsers)
        assertThat(saved.createDateTime).isEqualTo(expected.createDateTime.toString())
        assertThat(saved.updateDateTime).isEqualTo(expected.updateDateTime.toString())
    }
}