package com.shallwecode.project.entity.model

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
)