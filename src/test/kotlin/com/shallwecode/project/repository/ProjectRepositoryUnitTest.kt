package com.shallwecode.project.repository

import com.shallwecode.project.entity.*
import com.shallwecode.util.isDescending
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.util.stream.Collectors
import java.util.stream.IntStream


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
            techStacks = mutableListOf(TechStack("spring"), TechStack("mysql")),
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
        assertThat(saved.techStacks).hasSameElementsAs(expected.techStacks)
        assertThat(saved.createDateTime).isEqualTo(expected.createDateTime.toString())
        assertThat(saved.updateDateTime).isEqualTo(expected.updateDateTime.toString())
    }

    @Test
    fun `findAll(pageable) - id 내림차순으로 정렬된 프로젝트 페이징 테스트`() {
        // given
        val projects = IntStream.range(0, 8)
            .mapToObj { n ->
                Project(
                    status = ProjectStatus.RECRUITING,
                    title = "title${n}",
                    description = "desc${n}",
                    createdUserId = n.toLong(),
                )
            }.collect(Collectors.toList())

        projectRepository.saveAll(projects)

        // 첫번째 페이지 테스트
        // 1.when
        val firstPage = projectRepository.findAll(
            PageRequest.of(0, 3, Sort.by("_id").descending())
        )

        // 1.then
        assertThat(firstPage.isFirst).isTrue
        assertThat(firstPage.numberOfElements).isEqualTo(3)

        val firstPageIdList = firstPage.stream()
            .map { project -> project.id }
            .collect(Collectors.toList())
        assertThat(isDescending(firstPageIdList)).isTrue

        // 두번째 페이지 테스트
        // 2.when
        val secondPage = projectRepository.findAll(
            PageRequest.of(1, 3, Sort.by("_id").descending())
        )

        // 2.then
        assertThat(secondPage.numberOfElements).isEqualTo(3)

        val secondPageIdList = secondPage.stream()
            .map { project -> project.id }
            .collect(Collectors.toList())
        assertThat(isDescending(secondPageIdList)).isTrue


        // 마지막 페이지 테스트
        // 3.when
        val lastPage = projectRepository.findAll(
            PageRequest.of(2, 3, Sort.by("_id").descending())
        )

        // 3.then
        assertThat(lastPage.isLast).isTrue
        assertThat(lastPage.numberOfElements).isEqualTo(2)

        val lastPageIdList = lastPage.stream()
            .map { project -> project.id }
            .collect(Collectors.toList())
        assertThat(isDescending(lastPageIdList)).isTrue
    }

    @Test
    fun `findProjectJoinFetchJoinedUsers - 참여한 사람이 여러명인 경우`() {
        // given
        var givenProject = Project(
            status = ProjectStatus.RECRUITING,
            title = "title",
            description = "description",
            createdUserId = 1L,
            joinedUsers = mutableListOf(
                JoinedUser(
                    id = JoinedUserId(10L),
                    status = JoinedUserStatus.JOINED
                ),
                JoinedUser(
                    id = JoinedUserId(22L),
                    status = JoinedUserStatus.JOINED
                )
            ),
            githubUrl = "githubUrl"
        )
        givenProject = projectRepository.save(givenProject)

        // when, then
        projectRepository.findProjectJoinFetchJoinedUsers(givenProject.id)
            .also {
                requireNotNull(it)

                assertThat(it.joinedUsers.size).isEqualTo(2)

                assertThat(it.joinedUsers[0].id.userId).isEqualTo(givenProject.joinedUsers[0].id.userId)
                assertThat(it.joinedUsers[0].status).isEqualTo(givenProject.joinedUsers[0].status)

                assertThat(it.joinedUsers[1].id.userId).isEqualTo(givenProject.joinedUsers[1].id.userId)
                assertThat(it.joinedUsers[1].status).isEqualTo(givenProject.joinedUsers[1].status)
            }
    }

    @Test
    fun `findProjectJoinFetchJoinedUsers - 참여한 사람이 없는 경우`() {
        // given
        var project = Project(
            status = ProjectStatus.RECRUITING,
            title = "title",
            description = "description",
            createdUserId = 1L,
            githubUrl = "githubUrl"
        )
        project = projectRepository.save(project)

        // when, then
        projectRepository.findProjectJoinFetchJoinedUsers(project.id)
            .also {
                requireNotNull(it)
                assertThat(it.joinedUsers.size).isEqualTo(0)
            }
    }

    @Test
    fun `findProjectJoinFetchJoinedUsers - 프로젝트가 존재하지 않는 경우`() {
        // given
        val dummyId = 10L
        // when, then
        projectRepository.findProjectJoinFetchJoinedUsers(dummyId)
            .also {
                assertThat(it).isNull()
            }
    }

    @Test
    fun `findProjectJoinFetchTechStacks - 기술 스택이 여러개 등록된 경우`() {
        // given
        var givenProject = Project(
            status = ProjectStatus.RECRUITING,
            title = "title",
            description = "description",
            createdUserId = 1L,
            techStacks = mutableListOf(
                TechStack("spring boot"),
                TechStack("kotlin")
            ),
            githubUrl = "githubUrl"
        )
        givenProject = projectRepository.save(givenProject)

        // when, then
        projectRepository.findProjectJoinFetchTechStacks(givenProject.id)
            .also {
                requireNotNull(it)

                assertThat(it.techStacks.size).isEqualTo(givenProject.techStacks.size)

                assertThat(it.techStacks[0].name).isEqualTo(givenProject.techStacks[0].name)
                assertThat(it.techStacks[1].name).isEqualTo(givenProject.techStacks[1].name)
            }
    }

    @Test
    fun `findProjectJoinFetchTechStacks - 기술 스택이 등록되지 않은 경우`() {
        // given
        var givenProject = Project(
            status = ProjectStatus.RECRUITING,
            title = "title",
            description = "description",
            createdUserId = 1L,
            githubUrl = "githubUrl"
        )
        givenProject = projectRepository.save(givenProject)

        // when, then
        projectRepository.findProjectJoinFetchTechStacks(givenProject.id)
            .also {
                requireNotNull(it)
                assertThat(it.techStacks.size).isEqualTo(0)
            }
    }

    @Test
    fun `findProjectJoinFetchTechStacks - 프로젝트가 존재하지 않는 경우`() {
        // given
        val dummyId = 109L
        // when, then
        projectRepository.findProjectJoinFetchTechStacks(dummyId)
            .also {
                assertThat(it).isNull()
            }
    }

}