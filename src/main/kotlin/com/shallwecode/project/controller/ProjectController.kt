package com.shallwecode.project.controller

import com.shallwecode.common.constants.CustomHttpHeaders
import com.shallwecode.common.http.response.HttpResponse
import com.shallwecode.project.controller.request.ProjectCreateRequest
import com.shallwecode.project.service.ProjectService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class ProjectController(
    val projectService: ProjectService
) {
    @PostMapping("/project")
    fun createProject(
        @RequestBody request: ProjectCreateRequest,
        @RequestHeader(CustomHttpHeaders.USER_ID) creatorId: Long
    ): HttpResponse<Map<String, Long>> {
        val id = projectService.createProject(request, creatorId)

        return HttpResponse(
            message = "success",
            body = mapOf("id" to id)
        )
    }

}