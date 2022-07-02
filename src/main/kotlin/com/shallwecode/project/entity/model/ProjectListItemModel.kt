package com.shallwecode.project.entity.model

import com.shallwecode.common.exception.entity.EmptyIdEntityException
import com.shallwecode.project.entity.Project
import com.shallwecode.project.entity.ProjectStatus
import java.time.LocalDateTime

data class ProjectListItemModel(
    val id: Long,
    val status: ProjectStatus,
    val title: String,
    val description: String,
    val createdUserId: Long,
    val githubUrl: String? = null,
    val createDateTime: LocalDateTime,
    val updateDateTime: LocalDateTime,
) {
    companion object {
        fun from(project: Project): ProjectListItemModel {
            return ProjectListItemModel(
                id = project.id ?: throw EmptyIdEntityException(),
                status = project.status,
                title = project.title,
                description = project.description,
                createdUserId = project.createdUserId,
                githubUrl = project.githubUrl,
                createDateTime = project.createDateTime,
                updateDateTime = project.updateDateTime
            )
        }
    }
}

