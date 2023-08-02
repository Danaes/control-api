package com.bikeup.control.api.authentication.inbound.rest

import com.bikeup.control.api.authentication.core.application.port.input.service.AuthenticationServicePort
import com.bikeup.control.api.authentication.core.application.usecase.UserLogInQry
import com.bikeup.control.api.authentication.core.application.usecase.UserSingUpCmd
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/auth")
class AuthenticationResource(
    private val authenticationServicePort: AuthenticationServicePort
) {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    internal fun singUp(userSingUpCmd: UserSingUpCmd): Response {
        val response = authenticationServicePort.singUp(userSingUpCmd)
        return Response.ok(response).status(Response.Status.CREATED).build()
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    internal fun logIn(userLogInQry: UserLogInQry): Response {
        val response = authenticationServicePort.logIn(userLogInQry)
        return Response.ok(response).build()
    }
}