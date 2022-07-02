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
        val techStacks = listOf("spring boot", "kotlin")

        val request = ProjectCreateRequest(
            title = title,
            description = description,
            createdUserId = createdUserId,
            githubUrl = githubUrl,
            techStacks = techStacks
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

        expected.techStacks.forEachIndexed { index, techStack ->
            assertThat(techStack.name).isEqualTo(techStacks[index])
        }
    }
}