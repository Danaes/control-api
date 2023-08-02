package com.bikeup.control.api.authentication.inbound.exception.handler

import com.bikeup.control.api.authentication.core.domain.exception.AuthenticationException
import com.bikeup.control.api.authentication.core.domain.exception.UserNotFoundException
import com.bikeup.control.api.common.inbound.exception.handler.ErrorResponse
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.Response.Status
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class AuthenticationGlobalExceptionHandler : ExceptionMapper<AuthenticationException> {

    override fun toResponse(exception: AuthenticationException): Response {
        return when (exception) {
            is UserNotFoundException ->
                ErrorResponse(message = exception.message ?: "User not found", status = Status.NOT_FOUND).toResponse()

            else ->
                ErrorResponse(
                    message = exception.message ?: "Internal server error",
                    status = Status.INTERNAL_SERVER_ERROR
                ).toResponse()
        }
    }
}