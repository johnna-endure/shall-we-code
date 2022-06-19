package com.shallwecode.project.controller.request

import com.shallwecode.project.entity.ProjectSortField
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

data class ProjectPagingParameters(
    val page: Int = 0,
    val size: Int = 20,
    val sortField: ProjectSortField? = null,
    val isAscending: Boolean? = null
) {
    fun toPageable(): Pageable {
        return if (sortField == null) {
            PageRequest.of(page, size)
        } else {
            requireNotNull(isAscending)

            val direction =
                if (isAscending) Sort.Direction.ASC
                else Sort.Direction.DESC
            PageRequest.of(page, size, Sort.by(direction, sortField.fieldName))
        }
    }
}