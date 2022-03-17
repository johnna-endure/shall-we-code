package com.shallwecode.common.http.response

import org.springframework.http.HttpStatus

class HttpResponse<T>(
    val status: Int,
    val message: String,
    val body: T?
) {
}