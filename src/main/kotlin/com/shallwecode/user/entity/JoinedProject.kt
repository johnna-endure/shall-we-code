package com.shallwecode.user.entity

import javax.persistence.*

@Table(name = "joined_project")
@Entity
class JoinedProject(
    @EmbeddedId
    var id: JoinedProjectId,
    var status: JoinedProjectStatus
)

@Embeddable
data class JoinedProjectId(

    @Column(name = "user_id")
    var userId: Long,

    @Column(name = "project_id")
    var projectId: Long

) : java.io.Serializable

enum class JoinedProjectStatus {
    PROGRESS,
    COMPLETED,
    DROP
}
