package com.shallwecode.project.service

import com.shallwecode.project.controller.request.ProjectPagingParameters
import com.shallwecode.project.entity.model.ProjectListItemModel
import com.shallwecode.project.entity.model.from
import com.shallwecode.project.repository.ProjectRepository
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class ProjectQueryService(
    val projectRepository: ProjectRepository
) {
    /**
     * 최신순으로 프로젝트 리스트를 조회합니디.
     */
    fun getProjectList(
        pageParameters: ProjectPagingParameters
    ): Page<ProjectListItemModel> {
        return projectRepository.findAll(pageParameters.toPageable())
            .map { ProjectListItemModel.from(it) }
    }

}