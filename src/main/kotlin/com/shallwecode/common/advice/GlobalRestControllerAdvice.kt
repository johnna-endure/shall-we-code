package com.shallwecode.common.advice

import com.shallwecode.common.exception.BadRequestException
import com.shallwecode.common.exception.NotFoundDataException
import com.shallwecode.common.http.response.HttpResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalRestControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException::class)
    fun badRequestExceptionHandler(exception: BadRequestException): HttpResponse<String> {
        return HttpResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            message = exception.message ?: "잘못된 요청 정보입니다."
        )
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundDataException::class)
    fun notFountExceptionHandler(exception: NotFoundDataException): HttpResponse<String> {
        return HttpResponse(
            status = HttpStatus.NOT_FOUND.value(),
            message = exception.message ?: "해당 데이터를 찾을 수 없습니다."
        )
    }
}