package com.bikeup.control.api.bike.inbound.rest

import com.bikeup.control.api.bike.core.application.port.input.service.EquipmentServicePort
import com.bikeup.control.api.bike.core.application.usecase.EquipmentCreateCmd
import com.bikeup.control.api.bike.core.application.usecase.EquipmentUpdateCmd
import com.bikeup.control.api.common.core.domain.security.USER_ROLE
import jakarta.annotation.security.RolesAllowed
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("bike/{bikeId}/equipment")
@RolesAllowed(USER_ROLE)
class EquipmentResource(
    private val equipmentServicePort: EquipmentServicePort
) {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    internal fun createEquipment(
        @PathParam("bikeId") bikeId: String,
        equipmentCreateCmd: EquipmentCreateCmd
    ): Response {
        val response = equipmentServicePort.save(equipmentCreateCmd.addBikeId(bikeId))
        return Response.ok(response).status(Response.Status.CREATED).build()
    }

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    internal fun updateEquipment(
        @PathParam("bikeId") bikeId: String,
        equipmentUpdateCmd: EquipmentUpdateCmd
    ): Response {
        val response = equipmentServicePort.update(equipmentUpdateCmd.addBikeId(bikeId))
        return Response.ok(response).status(Response.Status.ACCEPTED).build()
    }

    @DELETE
    @Path("/{equipmentId}")
    internal fun deleteEquipment(
        @PathParam("bikeId") bikeId: String,
        @PathParam("equipmentId") equipmentId: String
    ): Response {
        equipmentServicePort.delete(bikeId, equipmentId)
        return Response.noContent().build()
    }
}