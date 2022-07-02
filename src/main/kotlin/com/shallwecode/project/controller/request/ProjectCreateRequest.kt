package com.shallwecode.project.controller.request

import com.shallwecode.project.entity.Project
import com.shallwecode.project.entity.ProjectStatus
import com.shallwecode.project.entity.TechStack

data class ProjectCreateRequest(
    val title: String,
    val description: String,
    val githubUrl: String?,
    val techStacks: List<String>
) {
    fun toEntity(creatorId: Long): Project {
        return Project(
            status = ProjectStatus.RECRUITING,
            title = title,
            description = description,
            createdUserId = creatorId,
            githubUrl = githubUrl,
            techStacks = techStacks.map { TechStack(it) }.toMutableList()
        )
    }
}