package com.shallwecode.common.exception.entity

class NotHasIdEntityException(message: String? = "엔티티의 id가 할당되지 않았습니다.") : RuntimeException(message)