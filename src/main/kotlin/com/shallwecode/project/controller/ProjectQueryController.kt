package com.shallwecode.project.controller

import com.shallwecode.common.http.response.HttpResponse
import com.shallwecode.project.controller.request.ProjectPagingParameters
import com.shallwecode.project.entity.model.ProjectListItemModel
import com.shallwecode.project.service.ProjectQueryService
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ProjectQueryController(
    val projectQueryService: ProjectQueryService
) {

    @GetMapping("/projects")
    fun getProjects(pageParameters: ProjectPagingParameters): HttpResponse<Page<ProjectListItemModel>> {
        return HttpResponse(
            message = "success",
            body = projectQueryService.getProjectList(pageParameters)
        )
    }
}