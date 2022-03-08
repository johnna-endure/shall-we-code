package com.shallwecode.exception

class BadRequestException : RuntimeException{
    constructor() : super()
    constructor(message: String) : super(message)
}