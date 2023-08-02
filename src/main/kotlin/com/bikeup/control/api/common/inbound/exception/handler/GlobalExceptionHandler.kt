package com.bikeup.control.api.common.inbound.exception.handler

import com.fasterxml.jackson.databind.exc.ValueInstantiationException
import jakarta.ws.rs.BadRequestException
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.Response.Status
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class GlobalExceptionHandler : ExceptionMapper<Exception> {
    override fun toResponse(exception: Exception): Response {
        return when (exception) {
            is BadRequestException ->
                ErrorResponse(
                    message = exception.message ?: "Invalid request",
                    status = Status.BAD_REQUEST
                ).toResponse()

            is ValueInstantiationException ->
                ErrorResponse(
                    message = exception.cause?.message ?: "Invalid request",
                    status = Status.BAD_REQUEST
                ).toResponse()

            else ->
                ErrorResponse(
                    message = exception.message ?: "Internal server error",
                    status = Status.INTERNAL_SERVER_ERROR
                ).toResponse()
        }
    }

}