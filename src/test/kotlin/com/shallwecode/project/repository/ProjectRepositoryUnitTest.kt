package com.shallwecode.project.repository

import com.shallwecode.project.entity.Project
import com.shallwecode.project.entity.ProjectStatus
import com.shallwecode.project.entity.TechStack
import com.shallwecode.util.isDescending
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.util.stream.Collectors
import java.util.stream.IntStream


@DataJpaTest
class ProjectRepositoryUnitTest(
    @Autowired
    val projectRepository: ProjectRepository,
    @Autowired
    val entityManager: TestEntityManager
) {

    @Test
    fun beanLoadingTest() {
        assertThat(projectRepository).isNotNull
    }

    @Test
    fun `save - 참여 유저가 없는 프로젝트 엔티티 저장`() {
        // given
        val givenProject = Project(
            status = ProjectStatus.RECRUITING,
            title = "title",
            description = "description",
            createdUserId = 1L,
            techStacks = mutableListOf(TechStack("spring"), TechStack("mysql")),
            githubUrl = "githubUrl"
        )

        // when
        var saved = projectRepository.saveAndFlush(givenProject)

        // then
        saved = projectRepository.findById(saved.id.value).get()
        assertThat(saved.id).isNotNull
        assertThat(saved.id).isEqualTo(givenProject.id)
        assertThat(saved.title).isEqualTo(givenProject.title)
        assertThat(saved.description).isEqualTo(givenProject.description)
        assertThat(saved.createdUserId).isEqualTo(givenProject.createdUserId)
        assertThat(saved.githubUrl).isEqualTo(givenProject.githubUrl)
        assertThat(saved.joinedUsers).hasSameElementsAs(givenProject.joinedUsers)
        assertThat(saved.techStacks).hasSameElementsAs(givenProject.techStacks)
        assertThat(saved.createDateTime).isEqualTo(givenProject.createDateTime.toString())
        assertThat(saved.updateDateTime).isEqualTo(givenProject.updateDateTime.toString())
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
            PageRequest.of(0, 3, Sort.by("projectId").descending())
        )

        // 1.then
        assertThat(firstPage.isFirst).isTrue
        assertThat(firstPage.numberOfElements).isEqualTo(3)

        val firstPageIdList = firstPage.stream()
            .map { project -> project.id.value }
            .collect(Collectors.toList())
        assertThat(isDescending(firstPageIdList)).isTrue

        // 두번째 페이지 테스트
        // 2.when
        val secondPage = projectRepository.findAll(
            PageRequest.of(1, 3, Sort.by("projectId").descending())
        )

        // 2.then
        assertThat(secondPage.numberOfElements).isEqualTo(3)

        val secondPageIdList = secondPage.stream()
            .map { project -> project.id.value }
            .collect(Collectors.toList())
        assertThat(isDescending(secondPageIdList)).isTrue


        // 마지막 페이지 테스트
        // 3.when
        val lastPage = projectRepository.findAll(
            PageRequest.of(2, 3, Sort.by("projectId").descending())
        )

        // 3.then
        assertThat(lastPage.isLast).isTrue
        assertThat(lastPage.numberOfElements).isEqualTo(2)

        val lastPageIdList = lastPage.stream()
            .map { project -> project.id.value }
            .collect(Collectors.toList())
        assertThat(isDescending(lastPageIdList)).isTrue
    }


    // TODO findById로 조회후 모델에 매핑할 때, 밸류 객체들이 초기화되는지 확인 필요
    @Test
    fun `findById - `() {

    }
}