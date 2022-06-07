package com.shallwecode.project.controller

import com.shallwecode.project.service.ProjectService
import org.springframework.web.bind.annotation.RestController

@RestController
class ProjectController(
    val projectService: ProjectService
) {
    fun createProject() {

    }
}