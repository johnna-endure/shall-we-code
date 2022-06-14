package com.shallwecode.project.entity

fun Project.addUser(user: JoinedUser) {
    val alreadyJoined = this.joinedUsers.filter { it.id == user.id }
        .firstOrNull() { it.status == JoinedUserStatus.JOINED }
        .let { it != null }

    if (!alreadyJoined) {
        this.joinedUsers.add(user)
    }
}
