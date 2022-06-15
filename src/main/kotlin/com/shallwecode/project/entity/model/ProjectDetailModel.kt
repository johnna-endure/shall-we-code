package com.shallwecode.project.entity.model

import com.shallwecode.project.entity.JoinedUser
import com.shallwecode.project.entity.Project
import com.shallwecode.project.entity.ProjectStatus
import com.shallwecode.project.entity.TechStack
import java.time.LocalDateTime

data class ProjectDetailModel(
    var status: ProjectStatus,
    val title: String,
    val description: String,
    val createdUserId: Long,
    val joinedUsers: MutableList<JoinedUser> = mutableListOf(),
    val techStacks: MutableList<TechStack> = mutableListOf(),
    val githubUrl: String? = null,
    val createDateTime: LocalDateTime,
    val updateDateTime: LocalDateTime,
) {
    companion object {
        fun from(project: Project): ProjectDetailModel {
            return ProjectDetailModel(
                status = project.status,
                title = project.title,
                description = project.description,
                createdUserId = project.createdUserId,
                joinedUsers = project.joinedUsers,
                techStacks = project.techStacks,
                githubUrl = project.githubUrl,
                createDateTime = project.createDateTime,
                updateDateTime = project.updateDateTime
            )
        }
    }
}