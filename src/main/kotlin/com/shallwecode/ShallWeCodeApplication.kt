package com.shallwecode

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication
class ShallWeCodeApplication

fun main(args: Array<String>) {
    runApplication<ShallWeCodeApplication>(*args)
}
