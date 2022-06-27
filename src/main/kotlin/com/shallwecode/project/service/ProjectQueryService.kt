package com.shallwecode.project.service

import com.shallwecode.common.exception.NotFoundDataException
import com.shallwecode.project.controller.request.ProjectPagingParameters
import com.shallwecode.project.entity.model.ProjectDetailModel
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

    fun getProjectList(
        pageParameters: ProjectPagingParameters
    ): Page<ProjectListItemModel> {
        return projectRepository.findAll(pageParameters.toPageable())
            .map { ProjectListItemModel.from(it) }
    }

    fun getProject(projectId: Long): ProjectDetailModel {
        val projectWithJoinedUser = projectRepository.findProjectWithJoinedUsers(projectId)
            ?: throw NotFoundDataException("project : $projectId")
        val projectWithTechStack = projectRepository.findProjectWithTechStacks(projectId)
            ?: throw NotFoundDataException("project : $projectId")

        val copiedProject = projectWithJoinedUser.copy()
        copiedProject.techStacks = projectWithTechStack.techStacks
        return ProjectDetailModel.from(copiedProject)
    }
}