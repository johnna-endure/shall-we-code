package com.shallwecode.user.entity

import com.shallwecode.project.entity.UserProject
import com.shallwecode.project.entity.UserProjectId

fun User.joinProject(vararg project: UserProject) {
    this.joinedProjects.addAll(project)
}

fun User.leaveProject(userProjectId: UserProjectId) {
//    return this.joinedProjects.removeIf { it.id == joinProjectId }
    this.joinedProjects.removeAt(0)
}
