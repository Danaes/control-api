package com.bikeup.control.api.bike.inbound.exception.handler

import com.bikeup.control.api.bike.core.domain.exception.BikeException
import com.bikeup.control.api.bike.core.domain.exception.BikeNotFoundException
import com.bikeup.control.api.bike.core.domain.exception.EquipmentNotFoundException
import com.bikeup.control.api.common.inbound.exception.handler.ErrorResponse
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.jboss.logging.Logger

@Provider
class BikeExceptionHandler : ExceptionMapper<BikeException> {
    override fun toResponse(exception: BikeException): Response {
        val errorResponse = getErrorResponse(exception)
        LOG.error(errorResponse.toString())

        return errorResponse.toResponse()
    }

    private fun getErrorResponse(exception: BikeException) =
        when (exception) {
            is BikeNotFoundException, is EquipmentNotFoundException ->
                ErrorResponse(
                    message = exception.message!!,
                    status = Response.Status.NOT_FOUND
                )

            else -> ErrorResponse(
                message = exception.message ?: "Internal server error",
                status = Response.Status.INTERNAL_SERVER_ERROR
            )
        }

    private companion object {
        val LOG: Logger = Logger.getLogger(BikeExceptionHandler::class.java)
    }
}