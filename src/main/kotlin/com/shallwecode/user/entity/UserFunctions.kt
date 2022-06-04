package com.shallwecode.user.entity

import com.shallwecode.project.entity.JoinProject
import com.shallwecode.project.entity.JoinProjectId

fun User.joinProject(vararg project: JoinProject) {
    this.joinedProjects.addAll(project)
}

fun User.leaveProject(joinProjectId: JoinProjectId) {
//    return this.joinedProjects.removeIf { it.id == joinProjectId }
    this.joinedProjects.removeAt(0)
}
