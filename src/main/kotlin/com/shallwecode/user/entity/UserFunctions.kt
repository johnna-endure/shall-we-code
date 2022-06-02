package com.shallwecode.user.entity

import com.shallwecode.project.entity.JoinProjectId
import com.shallwecode.project.entity.JoinProjectTable

fun UserTable.joinProject(vararg project: JoinProjectTable) {
    this.joinedProjects.addAll(project)
}

fun UserTable.leaveProject(joinProjectId: JoinProjectId) {
//    return this.joinedProjects.removeIf { it.id == joinProjectId }
    this.joinedProjects.removeAt(0)
}
