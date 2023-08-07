package com.bikeup.control.api.bike.inbound.rest

import com.bikeup.control.api.bike.core.application.port.input.service.BikeServicePort
import com.bikeup.control.api.bike.core.application.usecase.BikeCreateCmd
import com.bikeup.control.api.bike.core.application.usecase.BikeUpdateCmd
import com.bikeup.control.api.common.core.domain.security.USER_ROLE
import jakarta.annotation.security.RolesAllowed
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("user/{userId}/bike")
@RolesAllowed(USER_ROLE)
class BikeResource(
    private val bikeServicePort: BikeServicePort
) {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    internal fun createBike(@PathParam("userId") userId: String, bikeCreateCmd: BikeCreateCmd): Response {
        val response = bikeServicePort.save(bikeCreateCmd.addUserId(userId))
        return Response.ok(response).status(Response.Status.CREATED).build()
    }

    @GET
    internal fun getBikes(@PathParam("userId") userId: String): Response {
        val response = bikeServicePort.find(userId)
        return Response.ok(response).build()
    }

    @GET
    @Path("/{bikeId}")
    internal fun getBike(@PathParam("userId") userId: String, @PathParam("bikeId") bikeId: String): Response {
        val response = bikeServicePort.find(userId, bikeId)
        return Response.ok(response).build()
    }

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    internal fun updateBike(@PathParam("userId") userId: String, bikeUpdateCmd: BikeUpdateCmd): Response {
        val response = bikeServicePort.update(bikeUpdateCmd.addUserId(userId))
        return Response.ok(response).status(Response.Status.ACCEPTED).build()
    }

    @PATCH
    @Path("/{bikeId}")
    internal fun increaseDistance(
        @PathParam("userId") userId: String,
        @PathParam("bikeId") bikeId: String,
        @QueryParam("distance") distance: Double
    ): Response {
        val response = bikeServicePort.increaseDistance(userId, bikeId, distance)
        return Response.ok(response).status(Response.Status.ACCEPTED).build()
    }

    @DELETE
    @Path("/{bikeId}")
    internal fun deleteBike(@PathParam("userId") userId: String, @PathParam("bikeId") bikeId: String): Response {
        bikeServicePort.delete(userId, bikeId)
        return Response.noContent().build()
    }
}