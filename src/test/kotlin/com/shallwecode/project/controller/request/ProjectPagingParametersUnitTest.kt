package com.shallwecode.project.controller.request

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Sort

class ProjectPagingParametersUnitTest {

    @Test
    fun `toPageable - pageable 객체 생성 테스트`() {
        // given
        val pageParameters = ProjectPagingParameters(
            page = 0,
            size = 5,
            sortField = ProjectSortField.ID,
            isAscending = false
        )

        // when
        val pageable = pageParameters.toPageable()

        // then
        assertThat(pageable.pageNumber).isEqualTo(pageParameters.page)
        assertThat(pageable.pageSize).isEqualTo(pageParameters.size)
        assertThat(pageable.sort.getOrderFor("_id")).isEqualTo(Sort.Order.desc(ProjectSortField.ID.fieldName))
    }
}