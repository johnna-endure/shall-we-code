package com.shallwecode.project.service

import com.shallwecode.project.controller.request.ProjectCreateRequest
import com.shallwecode.project.repository.ProjectRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ProjectService(
    val projectRepository: ProjectRepository
) {
    fun createProject(request: ProjectCreateRequest): Long {
        return projectRepository.save(request.toEntity()).id
    }
}