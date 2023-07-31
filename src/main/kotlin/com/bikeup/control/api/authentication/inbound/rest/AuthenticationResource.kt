package com.bikeup.control.api.authentication.inbound.rest

import com.bikeup.control.api.authentication.core.application.port.service.AuthenticationPort
import com.bikeup.control.api.authentication.core.application.usecase.LogInUserQry
import com.bikeup.control.api.authentication.core.application.usecase.SingUpUserCmd
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/auth")
class AuthenticationResource(
    private val authenticationPort: AuthenticationPort
){

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    internal fun singUp(singUpUserCmd: SingUpUserCmd): Response {
        val response = authenticationPort.singUp(singUpUserCmd)
        return Response.ok(response).status(Response.Status.CREATED).build()
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    internal fun logIn(logInUserQry: LogInUserQry): Response {
        val response = authenticationPort.logIn(logInUserQry)
        return Response.ok(response).build()
    }
}