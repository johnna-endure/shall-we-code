package com.shallwecode.user.entity

fun User.joinProject(vararg project: JoinedProject) {
    this.joinedProjects.addAll(project)
}

fun User.leaveProject(joinedProjectId: JoinedProjectId): Boolean {
    return this.joinedProjects.removeIf { it.id == joinedProjectId }
}
