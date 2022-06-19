package com.shallwecode.project.entity.model

import com.shallwecode.project.entity.Project
import com.shallwecode.project.entity.ProjectStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ProjectListItemModelUnitTest {

    @Test
    fun `from - project 엔티티로부터 객체 생성 테스트`() {
        // given
        val project = Project(
            status = ProjectStatus.RECRUITING,
            title = "프로젝트 제목",
            description = "프로젝트 설명",
            createdUserId = 10L,
            githubUrl = "githubUrl"
        )
        project.id = 100L

        // when
        val model = ProjectListItemModel.from(project)

        // then
        assertThat(model.id).isEqualTo(project.id)
        assertThat(model.title).isEqualTo(project.title)
        assertThat(model.description).isEqualTo(project.description)
        assertThat(model.createdUserId).isEqualTo(project.createdUserId)
        assertThat(model.githubUrl).isEqualTo(project.githubUrl)
        assertThat(model.createDateTime).isEqualTo(project.createDateTime.toString())
        assertThat(model.updateDateTime).isEqualTo(project.updateDateTime.toString())
    }
}