package com.shallwecode.project.entity

import java.io.Serializable
import javax.persistence.*

@Embeddable
class JoinedUser(
    @Embedded
    var id: JoinedUserId,

    @Enumerated(EnumType.STRING)
    var status: JoinedUserStatus
)

@Embeddable
data class JoinedUserId(
    @Column(name = "user_id")
    var userId: Long,

    ) : Serializable

enum class JoinedUserStatus {
    JOINED,
    LEAVED,
    KICKED_OUT
}
