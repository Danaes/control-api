package com.bikeup.control.api.authentication.core.application

import com.bikeup.control.api.authentication.core.application.port.persistance.UserPort
import com.bikeup.control.api.authentication.core.application.port.service.AuthenticationPort
import com.bikeup.control.api.authentication.core.application.usecase.AuthenticationResponse
import com.bikeup.control.api.authentication.core.application.usecase.LogInUserQry
import com.bikeup.control.api.authentication.core.application.usecase.SingUpUserCmd
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.config.inject.ConfigProperty

@ApplicationScoped
class AuthenticationAdapter(
    @ConfigProperty(name = "mp.jwt.verify.issuer") private val issuer: String,
    private val userPort: UserPort
) : AuthenticationPort {

    override fun singUp(singUpUserCmd: SingUpUserCmd): AuthenticationResponse {
        val userCreated = userPort.save(singUpUserCmd)
        return AuthenticationResponse.build(userCreated, issuer)
    }

    override fun logIn(logInUserQry: LogInUserQry): AuthenticationResponse {
        val user = userPort.find(logInUserQry)
        return AuthenticationResponse.build(user, issuer)
    }
}