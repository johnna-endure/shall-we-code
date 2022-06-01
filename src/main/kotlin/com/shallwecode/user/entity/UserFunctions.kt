package com.shallwecode.user.entity

import com.shallwecode.project.entity.JoinProject

fun UserTable.joinProject(vararg project: JoinProject) {
    this.joinedProjects.addAll(project)
}