package com.shallwecode.project.entity

import java.io.Serializable
import javax.persistence.*

/**
 * 프로젝트에 참여한 사용자
 */
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
    /**
     * 참여 승인을 기다리는 상태
     */
    PENDING,

    /**
     * 참여 승인된 상태
     */
    PARTICIPATED,

    /**
     * 프로젝트를 자의로 떠난 상태
     */
    LEAVED,

    /**
     * 프로젝트에서 밴된 경우
     */
    KICKED_OUT
}
