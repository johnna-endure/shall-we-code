package com.shallwecode.common.exception

class BadRequestException : RuntimeException{
    constructor() : super()
    constructor(message: String) : super(message)
}