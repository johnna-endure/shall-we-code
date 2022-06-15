package com.shallwecode.user.entity

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Embedded

@Embeddable
class JoinedProject(
    @Embedded
    var id: JoinedProjectId,
    var status: JoinedProjectStatus
)

@Embeddable
data class JoinedProjectId(

    @Column(name = "project_id")
    var projectId: Long

) : Serializable

enum class JoinedProjectStatus {
    PROGRESS,
    COMPLETED,
    DROP
}
