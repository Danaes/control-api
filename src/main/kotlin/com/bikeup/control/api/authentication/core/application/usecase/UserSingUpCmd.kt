package com.bikeup.control.api.authentication.core.application.usecase

import jakarta.ws.rs.BadRequestException

data class UserSingUpCmd(
    val username: String,
    val surname: String,
    val email: String,
    val password: String
) {
    init {
        if (username.isBlank()) throw BadRequestException("Username parameter may not be blank")
        if (surname.isBlank()) throw BadRequestException("Surname parameter may not be blank")
        if (email.isBlank()) throw BadRequestException("Email parameter may not be blank")
        if (password.isBlank()) throw BadRequestException("Password parameter may not be blank")
    }
}
