package com.shallwecode.user.entity

fun User.joinProject(vararg project: JoinProject) {
    this.joinProjects.addAll(project)
}

fun User.leaveProject(joinProjectId: JoinProjectId): Boolean {
    return this.joinProjects.removeIf { it.id == joinProjectId }
}
