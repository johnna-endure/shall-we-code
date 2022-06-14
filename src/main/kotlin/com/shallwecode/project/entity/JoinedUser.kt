package com.shallwecode.project.entity

import javax.persistence.*

@Table(name = "joined_user")
@Entity
class JoinedUser(
    @EmbeddedId
    var id: JoinedUserId,

    @Enumerated(EnumType.STRING)
    var status: JoinedUserStatus
)

@Embeddable
data class JoinedUserId(
    @Column(name = "user_id")
    var userId: Long,

    @Column(name = "project_id")
    var projectId: Long
) : java.io.Serializable

enum class JoinedUserStatus {
    JOINED,
    LEAVED,
    KICKED_OUT
}
