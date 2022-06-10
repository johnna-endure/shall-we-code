package com.shallwecode.project.service

import com.shallwecode.common.util.modelmapper.ModelMapper
import com.shallwecode.project.entity.Project
import com.shallwecode.project.entity.model.ProjectListItemModel
import com.shallwecode.project.repository.ProjectRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ProjectQueryService(
    val projectRepository: ProjectRepository
) {
    /**
     * 최신순으로 프로젝트 리스트를 조회합니디.
     */
    @Transactional(readOnly = true)
    fun getProjectListOrderByIdDescending(page: Int, size: Int): Page<ProjectListItemModel> {
        val pageRequest = PageRequest.of(page, size, Sort.by("_id").descending())
        return projectRepository.findAll(pageRequest)
            .map { ModelMapper.mapper<Project, ProjectListItemModel>(it) }
    }
}