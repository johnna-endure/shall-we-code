package com.shallwecode.client.authentication.request

import java.time.LocalDateTime

data class UserAuthenticationRequest(
    val userId: Long,
    val email: String,
    val password: String,
    val roles: List<String>,
    val createDateTime: LocalDateTime
)