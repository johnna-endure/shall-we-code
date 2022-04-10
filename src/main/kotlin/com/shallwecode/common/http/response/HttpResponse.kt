package com.shallwecode.common.http.response

class HttpResponse<T>(
    val message: String? = null,
    val body: T? = null
) {
}