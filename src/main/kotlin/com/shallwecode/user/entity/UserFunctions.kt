package com.shallwecode.user.entity

fun User.joinProject(vararg project: UserProject) {
    this.joinedProjects.addAll(project)
}

fun User.leaveProject(userProjectId: UserProjectId): Boolean {
    return this.joinedProjects.removeIf { it.id == userProjectId }
}
