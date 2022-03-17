package com.shallwecode

import com.shallwecode.common.util.modelmapper.ModelMapper
import com.shallwecode.user.entity.User
import org.junit.jupiter.api.Test
import kotlin.reflect.KParameter
import kotlin.reflect.full.*
import kotlin.reflect.jvm.javaConstructor
import kotlin.reflect.jvm.javaMethod
import kotlin.reflect.jvm.reflect

class KotlinReflection {

    @Test
    fun properties_test() {
        PersonModel()
    }

    class Person(var name: String, var age: Int) {
    }

    data class PersonModel(val name: String = "", val age: Int = 0, val gender: String = "") {
    }

}