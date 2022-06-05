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
class JoinedProjectId(

    @Column(name = "user_id")
    var userId: Long,

    @Column(name = "project_id")
    var projectId: Long

) : java.io.Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as JoinedProjectId

        if (userId != other.userId) return false
        if (projectId != other.projectId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userId.hashCode()
        result = 31 * result + projectId.hashCode()
        return result
    }
}

enum class JoinedProjectStatus {
    PROGRESS,
    COMPLETED,
    DROP
}
