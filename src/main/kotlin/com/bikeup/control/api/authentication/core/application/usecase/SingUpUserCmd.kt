package com.bikeup.control.api.authentication.core.application.usecase

import jakarta.ws.rs.BadRequestException

data class SingUpUserCmd(
    val username: String,
    val surname: String,
    val email: String,
    val password: String
) {
    init {
        if (username.isBlank()) throw BadRequestException("Username may not be blank")
        if (surname.isBlank()) throw BadRequestException("Surname may not be blank")
        if (email.isBlank()) throw BadRequestException("Email may not be blank")
        if (password.isBlank()) throw BadRequestException("Password may not be blank")
    }
}
