package com.bikeup.control.api.authentication.core.application

import com.bikeup.control.api.authentication.core.application.port.input.service.AuthenticationServicePort
import com.bikeup.control.api.authentication.core.application.port.output.persistance.UserRepositoryPort
import com.bikeup.control.api.authentication.core.application.usecase.AuthenticationResponse
import com.bikeup.control.api.authentication.core.application.usecase.UserLogInQry
import com.bikeup.control.api.authentication.core.application.usecase.UserSingUpCmd
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.config.inject.ConfigProperty

@ApplicationScoped
class AuthenticationServiceAdapter(
    @ConfigProperty(name = "mp.jwt.verify.issuer") private val issuer: String,
    private val userRepositoryPort: UserRepositoryPort
) : AuthenticationServicePort {

    override fun singUp(userSingUpCmd: UserSingUpCmd): AuthenticationResponse {
        val userCreated = userRepositoryPort.save(userSingUpCmd)
        return AuthenticationResponse.map(userCreated, issuer)
    }

    override fun logIn(userLogInQry: UserLogInQry): AuthenticationResponse {
        val user = userRepositoryPort.find(userLogInQry)
        return AuthenticationResponse.map(user, issuer)
    }
}