package com.shallwecode.project.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ProjectUnitTest {

    @Test
    fun `project 객체 생성 테스트`() {
        // given
        val status = ProjectStatus.RECRUITING
        val title = "title"
        val description = "프로젝트 설명"
        val createdUser = 1L
        val githubUrl = "githubUrl"

        // when
        val project = Project(
            status = status,
            title = title,
            description = description,
            createdUserId = createdUser,
            githubUrl = githubUrl
        )

        //then
        assertThat(project.status).isEqualTo(status)
        assertThat(project.title).isEqualTo(title)
        assertThat(project.description).isEqualTo(description)
        assertThat(project.createdUserId).isEqualTo(createdUser)
        assertThat(project.joinedUsers).isEmpty()
        assertThat(project.githubUrl).isEqualTo(githubUrl)
        assertThat(project.createDateTime).isNotNull
        assertThat(project.updateDateTime).isNotNull
    }

    @Test
    fun `id가 초기화되지 않았을 때, id 값 테스트`() {
        // given
        val status = ProjectStatus.RECRUITING
        val title = "title"
        val description = "프로젝트 설명"
        val createdUser = 1L
        val githubUrl = "githubUrl"

        // when
        val project = Project(
            status = status,
            title = title,
            description = description,
            createdUserId = createdUser,
            githubUrl = githubUrl
        )

        // when, then
        assertThat(project.id).isNull()
    }

    @Test
    fun `join - 사용자가 프로젝트에 처음 참여신청을 보내는 경우`() {
        // given
        val status = ProjectStatus.RECRUITING
        val title = "title"
        val description = "프로젝트 설명"
        val createdUser = 1L
        val githubUrl = "githubUrl"

        val userId = 10L

        val project = Project(
            status = status,
            title = title,
            description = description,
            createdUserId = createdUser,
            githubUrl = githubUrl,
        )

        // when
        project.join(userId)

        // then
        assertThat(project.joinedUsers.size).isEqualTo(1)
        assertThat(project.joinedUsers[0].id.userId).isEqualTo(userId)
        assertThat(project.joinedUsers[0].status).isEqualTo(JoinedUserStatus.PENDING)
    }

    @Test
    fun `join - 이미 참여 승인된 사용자를 다시 추가하는 경우`() {
        // given
        val status = ProjectStatus.RECRUITING
        val title = "title"
        val description = "프로젝트 설명"
        val createdUser = 1L
        val githubUrl = "githubUrl"

        val userId = 2L

        val participatedUser = JoinedUser(
            id = JoinedUserId(userId),
            status = JoinedUserStatus.PARTICIPATED
        )

        val project = Project(
            status = status,
            title = title,
            description = description,
            createdUserId = createdUser,
            githubUrl = githubUrl,
            joinedUsers = mutableListOf(participatedUser),
        )

        // when
        project.join(userId)

        // then
        assertThat(project.joinedUsers.size).isEqualTo(1)
        assertThat(project.joinedUsers[0].id.userId).isEqualTo(userId)
        assertThat(project.joinedUsers[0].status).isEqualTo(JoinedUserStatus.PARTICIPATED)
    }

    @Test
    fun `join - 이미 참여 승인대기 중인 사용자를 다시 추가하는 경우`() {
        // given
        val status = ProjectStatus.RECRUITING
        val title = "title"
        val description = "프로젝트 설명"
        val createdUser = 1L
        val githubUrl = "githubUrl"

        val userId = 2L

        val participatedUser = JoinedUser(
            id = JoinedUserId(userId),
            status = JoinedUserStatus.PENDING
        )

        val project = Project(
            status = status,
            title = title,
            description = description,
            createdUserId = createdUser,
            githubUrl = githubUrl,
            joinedUsers = mutableListOf(participatedUser),
        )

        // when
        project.join(userId)

        // then
        assertThat(project.joinedUsers.size).isEqualTo(1)
        assertThat(project.joinedUsers[0].id.userId).isEqualTo(userId)
        assertThat(project.joinedUsers[0].status).isEqualTo(JoinedUserStatus.PENDING)
    }

    @Test
    fun `copy - 깊은 복사 테스트`() {
        // given
        val givenProject = Project(
            status = ProjectStatus.RECRUITING,
            title = "title",
            description = "프로젝트 설명",
            createdUserId = 1L,
            githubUrl = "githubUrl",
            joinedUsers = mutableListOf(
                JoinedUser(
                    id = JoinedUserId(2L),
                    status = JoinedUserStatus.PARTICIPATED
                )
            ),
            techStacks = mutableListOf(
                TechStack("spring boot"),
                TechStack("kotlin")
            )
        )

        val copied = givenProject.copy()

        assertThat(copied.status).isEqualTo(givenProject.status)
        assertThat(copied.title).isEqualTo(givenProject.title)
        assertThat(copied.description).isEqualTo(givenProject.description)
        assertThat(copied.createdUserId).isEqualTo(givenProject.createdUserId)
        assertThat(copied.githubUrl).isEqualTo(givenProject.githubUrl)

        copied.joinedUsers.forEachIndexed { index, joinedUser ->
            assertThat(givenProject.joinedUsers[index].id).isEqualTo(joinedUser.id)
            assertThat(givenProject.joinedUsers[index].status).isEqualTo(joinedUser.status)
        }

        copied.techStacks.forEachIndexed { index, techStack ->
            assertThat(givenProject.techStacks[index].name).isEqualTo(techStack.name)
        }
    }

}