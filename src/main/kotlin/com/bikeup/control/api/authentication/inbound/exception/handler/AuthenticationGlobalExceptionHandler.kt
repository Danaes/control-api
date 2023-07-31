package com.bikeup.control.api.authentication.inbound.exception.handler

import com.bikeup.control.api.authentication.common.core.application.exception.handler.ErrorResponse
import com.bikeup.control.api.authentication.core.domain.exception.UserNotFoundException
import jakarta.ws.rs.BadRequestException
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.Response.Status
import jakarta.ws.rs.core.Response.Status.*
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class AuthenticationGlobalExceptionHandler: ExceptionMapper<Exception> {

    override fun toResponse(exception: Exception): Response {
        return when (exception){
            is UserNotFoundException ->
                buildResponse(errorMessage = exception.message ?: "User not found", status = NOT_FOUND)

            is BadRequestException ->
                buildResponse(errorMessage = exception.message ?: "Invalid request", status = BAD_REQUEST)

            else ->
                buildResponse(errorMessage = exception.message ?: "Internal server error", status = INTERNAL_SERVER_ERROR)
        }
    }

    private fun buildResponse(errorMessage: String, status: Status): Response =
        Response.ok(ErrorResponse(errorMessage)).status(status).build()
}