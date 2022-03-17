package com.shallwecode.common.exception.entity

class NoIdEntityException(message: String = "해당 엔티티에 id가 존재하지 않습니다.") : RuntimeException(message){


}