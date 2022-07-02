package com.shallwecode.project.exception

sealed class ProjectException(message: String) : RuntimeException(message)

class ProjectJoinException(errorMessage: String) : ProjectException(errorMessage)