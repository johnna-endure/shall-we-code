package com.shallwecode.project.repository

import com.shallwecode.project.entity.Project
import com.shallwecode.project.entity.ProjectStatus
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


}