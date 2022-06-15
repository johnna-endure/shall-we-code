package com.shallwecode.project.entity

import com.shallwecode.common.exception.entity.EmptyIdEntityException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
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
    fun `id가 초기화되지 않았을 때, id에 접근할 때 예외 발생 테스트`() {
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
        assertThatThrownBy { project.id }
            .isExactlyInstanceOf(EmptyIdEntityException::class.java)

    }

    @Test
    fun `addUser - 첫번째 사용자가 프로젝트에 참여`() {
        // given
        val status = ProjectStatus.RECRUITING
        val title = "title"
        val description = "프로젝트 설명"
        val createdUser = 1L
        val githubUrl = "githubUrl"
        val projectId = 1L

        val joinedUser = JoinedUser(
            id = JoinedUserId(2L),
            status = JoinedUserStatus.JOINED
        )

        val project = Project(
            status = status,
            title = title,
            description = description,
            createdUserId = createdUser,
            githubUrl = githubUrl,
            joinedUsers = mutableListOf(),
        )
        project._id = projectId

        // when
        project.addUser(joinedUser)

        // then
        assertThat(project.joinedUsers.size).isEqualTo(1)
        assertThat(project.joinedUsers[0].id.userId).isEqualTo(2L)
        assertThat(project.joinedUsers[0].status).isEqualTo(JoinedUserStatus.JOINED)
    }

    @Test
    fun `addUser - 이미 참여한 사용자를 다시 추가하는 경우`() {
        // given
        val status = ProjectStatus.RECRUITING
        val title = "title"
        val description = "프로젝트 설명"
        val createdUser = 1L
        val githubUrl = "githubUrl"
        val projectId = 1L

        val joinedUser = JoinedUser(
            id = JoinedUserId(2L),
            status = JoinedUserStatus.JOINED
        )

        val project = Project(
            status = status,
            title = title,
            description = description,
            createdUserId = createdUser,
            githubUrl = githubUrl,
            joinedUsers = mutableListOf(joinedUser),
        )
        project._id = projectId

        // when
        project.addUser(joinedUser)

        // then
        assertThat(project.joinedUsers.size).isEqualTo(1)
        assertThat(project.joinedUsers[0].id.userId).isEqualTo(2L)
        assertThat(project.joinedUsers[0].status).isEqualTo(JoinedUserStatus.JOINED)
    }

}