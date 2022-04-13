package com.shallwecode.user.controller.join

import com.shallwecode.common.http.response.HttpResponse
import com.shallwecode.user.controller.join.request.JoinRequest
import com.shallwecode.user.service.join.JoinService
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * 사용자 회원가입 관련 API 를 다루는 컨트롤러입니다.
 */
@RestController
class JoinController(
    private val joinService: JoinService
) {

    @ResponseStatus(CREATED)
    @PostMapping("/join")
    fun join(@RequestBody request: JoinRequest): HttpResponse<Map<String, Long>> {
        return HttpResponse(
            message = "created",
            body = mapOf("id" to joinService.join(request))
        )
    }

}