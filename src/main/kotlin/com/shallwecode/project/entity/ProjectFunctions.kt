package com.shallwecode.project.entity

/**
 * 프로젝트에 참여합니다.
 * 참여된 유저의 초기 상태는 프로젝트 생성자의
 * 참여 승인이 끝나기까지 PENDING 상태가 됩니다.
 *
 */
fun Project.join(userId: Long) {
    val alreadyJoined = this.joinedUsers.filter { it.id.userId == userId }
        .firstOrNull() {
            it.status == JoinedUserStatus.PARTICIPATED || it.status == JoinedUserStatus.PENDING
        }
        .let { it != null }

    if (!alreadyJoined) {
        this.joinedUsers.add(
            JoinedUser(
                id = JoinedUserId(userId),
                status = JoinedUserStatus.PENDING
            )
        )
    }
}
