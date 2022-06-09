package com.shallwecode.common.util.modelmapper

import kotlin.reflect.KParameter

class ModelMapper {
    companion object {
        /**
         * 반환 클래스의 주 생성자를 통해 값을 바인딩하고 반환하는 mapper
         */
        inline fun <reified T, reified R> mapper(source: T): R {
            R::class.constructors
                .last { constructor ->
                    val mutableMap: MutableMap<KParameter, Any?> = mutableMapOf<KParameter, Any?>()
                    constructor.parameters.forEach { parameter ->
                        mutableMap[parameter] = T::class.members.find { it.name == parameter.name }?.call(source)
                    }
                    return constructor.callBy(mutableMap)
                }
            throw RuntimeException("${R::class.simpleName} 클래스의 생성자가 존재하지 않습니다.")
        }
    }
}