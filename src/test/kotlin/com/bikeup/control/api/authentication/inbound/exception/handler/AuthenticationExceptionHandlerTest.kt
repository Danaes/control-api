package com.bikeup.control.api.authentication.inbound.exception.handler

import com.bikeup.control.api.authentication.core.domain.exception.AuthenticationException
import com.bikeup.control.api.authentication.core.domain.exception.UserNotFoundException
import com.bikeup.control.api.common.inbound.exception.handler.ErrorResponse
import jakarta.ws.rs.core.Response.Status
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthenticationExceptionHandlerTest {

    private val sut = AuthenticationExceptionHandler()

    private fun createAuthenticationExceptionParams() = listOf(
        Arguments.of(UserNotFoundException("Invalid email or password"), Status.NOT_FOUND),
        Arguments.of(OtherAuthenticationException("Internal server error"), Status.INTERNAL_SERVER_ERROR),
    )

    @ParameterizedTest
    @MethodSource("createAuthenticationExceptionParams")
    fun toResponse_whenAuthenticationException_shouldReturnExpectedMessageAndStatus(
        exception: AuthenticationException,
        expectedStatus: Status
    ) {
        val result = sut.toResponse(exception)

        assertEquals(exception.message!!, (result.entity as ErrorResponse).message)
        assertEquals(expectedStatus.statusCode, result.status)
    }

    class OtherAuthenticationException(message: String) : AuthenticationException(message)
}