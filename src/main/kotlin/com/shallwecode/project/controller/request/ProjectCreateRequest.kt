package com.shallwecode.project.controller.request

import com.shallwecode.project.entity.Project
import com.shallwecode.project.entity.ProjectStatus

data class ProjectCreateRequest(
    val title: String,
    val description: String,
    val createdUserId: Long,
    val githubUrl: String?
) {
    fun toEntity(): Project {
        return Project(
            status = ProjectStatus.RECRUITING,
            title = title,
            description = description,
            createdUserId = createdUserId,
            githubUrl = githubUrl
        )
    }
}