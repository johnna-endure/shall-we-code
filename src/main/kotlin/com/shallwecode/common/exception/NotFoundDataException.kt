package com.shallwecode.common.exception

class NotFoundDataException(message: String? = "해당 데이터를 찾을 수 없습니다.") : RuntimeException(message)  {
}