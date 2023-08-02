package com.bikeup.control.api.authentication.core.application.usecase

import jakarta.ws.rs.BadRequestException

data class UserLogInQry(
    val email: String,
    val password: String,
) {
    init {
        if (email.isBlank()) throw BadRequestException("Email parameter may not be blank")
        if (password.isBlank()) throw BadRequestException("Password parameter may not be blank")
    }
}
