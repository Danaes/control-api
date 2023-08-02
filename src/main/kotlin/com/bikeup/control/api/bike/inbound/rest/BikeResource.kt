package com.bikeup.control.api.bike.inbound.rest

import com.bikeup.control.api.common.core.domain.security.USER_ROLE
import jakarta.annotation.security.RolesAllowed
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.core.Response

@Path("user/{userId}/bike")
class BikeResource {

    @GET
    @RolesAllowed(USER_ROLE)
    internal fun getBikes(@PathParam("userId") userId: String): Response {
        return Response.ok(userId).build()
    }
}