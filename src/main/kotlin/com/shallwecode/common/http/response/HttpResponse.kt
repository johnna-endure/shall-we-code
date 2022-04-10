package com.shallwecode.common.http.response

class HttpResponse<T>(
    val status: Int,
    val message: String? = null,
    val body: T? = null
) {
}