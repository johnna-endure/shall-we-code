package com.shallwecode.project.controller.request

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ProjectCreateRequestUnitTest {
    @Test
    fun toEntityTest() {
        // given
        val title = "title"
        val description = "description"
        val createdUserId = 1L
        val githubUrl = "url"

        val request = ProjectCreateRequest(
            title = title,
            description = description,
            createdUserId = createdUserId,
            githubUrl = githubUrl
        )
        // when
        val expected = request.toEntity()

        // then
        assertThat(expected.title).isEqualTo(title)
        assertThat(expected.description).isEqualTo(description)
        assertThat(expected.createdUserId).isEqualTo(createdUserId)
        assertThat(expected.githubUrl).isEqualTo(githubUrl)
        assertThat(expected.createDateTime).isNotNull
        assertThat(expected.updateDateTime).isNotNull
    }
}