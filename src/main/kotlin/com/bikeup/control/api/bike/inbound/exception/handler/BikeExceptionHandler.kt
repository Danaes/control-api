package com.bikeup.control.api.bike.inbound.exception.handler

import com.bikeup.control.api.bike.core.domain.exception.BikeException
import com.bikeup.control.api.bike.core.domain.exception.BikeNotFoundException
import com.bikeup.control.api.common.inbound.exception.handler.ErrorResponse
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class BikeExceptionHandler : ExceptionMapper<BikeException> {
    override fun toResponse(exception: BikeException): Response {
        return when (exception) {
            is BikeNotFoundException ->
                ErrorResponse(
                    message = exception.message ?: "Bike not found",
                    status = Response.Status.NOT_FOUND
                ).toResponse()

            else ->
                ErrorResponse(
                    message = exception.message ?: "Internal server error",
                    status = Response.Status.INTERNAL_SERVER_ERROR
                ).toResponse()
        }
    }
}