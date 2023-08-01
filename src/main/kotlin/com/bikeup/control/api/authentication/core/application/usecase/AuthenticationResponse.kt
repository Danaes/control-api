package com.bikeup.control.api.authentication.core.application.usecase

import com.bikeup.control.api.authentication.core.domain.model.User
import io.smallrye.jwt.build.Jwt

data class AuthenticationResponse(
    val fullName: String,
    val email: String,
    val token: String
) {

    companion object {
        fun build(user: User, issuer: String): AuthenticationResponse =
            AuthenticationResponse(
                fullName = "${user.username} ${user.surname}",
                email = user.email,
                token = buildToken(user, issuer)
            )

        private fun buildToken(user: User, issuer: String): String =
            Jwt.issuer(issuer).upn(user.email).groups(user.roles.toString()).sign()
    }
}