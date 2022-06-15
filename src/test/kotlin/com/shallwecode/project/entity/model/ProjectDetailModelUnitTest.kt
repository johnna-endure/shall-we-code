package com.shallwecode.project.entity.model

import com.shallwecode.project.entity.Project
import com.shallwecode.project.entity.ProjectStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ProjectDetailModelUnitTest {

    @Test
    fun `from(project) - ProjectDetailModel 객체 생성`() {
        // given
        val givenProject = Project(
            status = ProjectStatus.RECRUITING,
            title = "title",
            description = "description",
            createdUserId = 12L,
        )

        // when
        val model = ProjectDetailModel.from(givenProject)

        // then
        assertThat(model.status).isEqualTo(givenProject.status)
        assertThat(model.title).isEqualTo(givenProject.title)
        assertThat(model.description).isEqualTo(givenProject.description)
        assertThat(model.createdUserId).isEqualTo(givenProject.createdUserId)
        assertThat(model.joinedUsers).isEqualTo(givenProject.joinedUsers)
        assertThat(model.techStacks).isEqualTo(givenProject.techStacks)
        assertThat(model.githubUrl).isEqualTo(givenProject.githubUrl)
        assertThat(model.createDateTime).isEqualTo(givenProject.createDateTime.toString())
        assertThat(model.updateDateTime).isEqualTo(givenProject.updateDateTime.toString())

    }
}