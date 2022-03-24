package com.shallwecode.common.exception

class BadRequestException(message: String? = "잘못된 요청입니다.") : RuntimeException(message) {
}