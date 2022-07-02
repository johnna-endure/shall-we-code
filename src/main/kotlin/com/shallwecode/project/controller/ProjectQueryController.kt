package com.shallwecode.project.controller

import com.shallwecode.common.http.response.HttpResponse
import com.shallwecode.project.controller.request.ProjectPagingParameters
import com.shallwecode.project.entity.model.ProjectDetailModel
import com.shallwecode.project.entity.model.ProjectListItemModel
import com.shallwecode.project.service.ProjectQueryService
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ProjectQueryController(
    val projectQueryService: ProjectQueryService
) {

    /**
     * @param pageParameters page, size, sortField, isAscending 를 담고 있는 클래스
     * @return 프로젝트 목록
     */
    @GetMapping("/projects")
    fun getProjects(
        @RequestParam pageParameters: ProjectPagingParameters
    ): HttpResponse<Page<ProjectListItemModel>> {
        return HttpResponse(
            message = "success",
            body = projectQueryService.getProjectList(pageParameters)
        )
    }

    @GetMapping("/projects/{projectId}")
    fun getProjectDetail(@PathVariable("projectId") id: Long): HttpResponse<ProjectDetailModel> {
        return HttpResponse(
            message = "success",
            body = projectQueryService.getProject(id)
        )
    }
}