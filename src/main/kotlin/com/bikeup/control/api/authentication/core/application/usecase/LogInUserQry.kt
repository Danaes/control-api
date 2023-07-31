package com.bikeup.control.api.authentication.core.application.usecase

import jakarta.ws.rs.BadRequestException

data class LogInUserQry(
    val email: String,
    val password: String,
){
    init {
        if (email.isBlank()) throw BadRequestException("Email may not be blank")
        if (password.isBlank()) throw BadRequestException("Password may not be blank")
    }
}
