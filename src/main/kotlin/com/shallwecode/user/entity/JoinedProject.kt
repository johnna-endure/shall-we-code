package com.shallwecode.user.entity

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

) : java.io.Serializable

enum class JoinedProjectStatus {
    PROGRESS,
    COMPLETED,
    DROP
}
