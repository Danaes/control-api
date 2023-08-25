package com.bikeup.control.api.authentication.inbound.rest.exception.handler

import com.bikeup.control.api.authentication.core.domain.exception.AuthenticationException
import com.bikeup.control.api.authentication.core.domain.exception.UserNotFoundException
import com.bikeup.control.api.common.inbound.exception.handler.ErrorResponse
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.Response.Status
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.jboss.logging.Logger

@Provider
class AuthenticationExceptionHandler : ExceptionMapper<AuthenticationException> {

    private val log = Logger.getLogger(AuthenticationExceptionHandler::class.java)

    override fun toResponse(exception: AuthenticationException): Response {
        val errorResponse = getErrorResponse(exception)
        log.error(errorResponse.toString())

        return errorResponse.toResponse()
    }

    private fun getErrorResponse(exception: AuthenticationException) =
        when (exception) {
            is UserNotFoundException ->
                ErrorResponse(message = exception.message!!, status = Status.NOT_FOUND)

            else -> ErrorResponse(message = exception.message!!, status = Status.INTERNAL_SERVER_ERROR)
        }
}