package com.shallwecode.user.controller.join

import com.shallwecode.common.http.response.HttpResponse
import com.shallwecode.user.controller.join.request.DuplicateCheckRequest
import com.shallwecode.user.controller.join.request.UserJoinRequest
import com.shallwecode.user.service.UserJoinService
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * 사용자 회원가입 관련 API 를 다루는 컨트롤러입니다.
 */
@RestController
class UserJoinController(
    private val userJoinService: UserJoinService
) {

    @ResponseStatus(CREATED)
    @PostMapping("/user/join")
    fun join(@RequestBody request: UserJoinRequest): HttpResponse<Map<String, Long>> {
        return HttpResponse(
            message = "created",
            body = mapOf("id" to userJoinService.join(request))
        )
    }

    @PostMapping("/user/join/duplicate-check")
    fun duplicateCheck(@RequestBody request: DuplicateCheckRequest): HttpResponse<Map<String, Boolean>> {
        return HttpResponse(
            message = "success",
            body = mapOf(userJoinService.duplicateEmailCheck(request.email))
        )
    }
}