package com.shallwecode.project.service

import com.shallwecode.common.exception.entity.EmptyIdEntityException
import com.shallwecode.project.controller.request.ProjectCreateRequest
import com.shallwecode.project.repository.ProjectRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ProjectService(
    val projectRepository: ProjectRepository
) {
    fun createProject(
        request: ProjectCreateRequest,
        creatorId: Long
    ): Long {
        return projectRepository.save(request.toEntity(creatorId)).id ?: throw EmptyIdEntityException()
    }
}