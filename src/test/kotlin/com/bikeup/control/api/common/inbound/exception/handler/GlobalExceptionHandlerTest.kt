package com.bikeup.control.api.common.inbound.exception.handler

import jakarta.ws.rs.BadRequestException
import jakarta.ws.rs.core.Response.Status
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GlobalExceptionHandlerTest {

    private val sut = GlobalExceptionHandler()

    private fun createExceptionParams() = listOf(
        Arguments.of(IllegalStateException("Entity id must exist"), Status.BAD_REQUEST, "Entity id must exist"),
        Arguments.of(IllegalStateException(), Status.BAD_REQUEST, "Invalid request"),
        Arguments.of(
            BadRequestException("Entity with id 1 not exists"),
            Status.BAD_REQUEST,
            "Entity with id 1 not exists"
        ),
        Arguments.of(Exception("An error is happening"), Status.INTERNAL_SERVER_ERROR, "An error is happening"),
        Arguments.of(Exception(), Status.INTERNAL_SERVER_ERROR, "Internal server error")
    )

    @ParameterizedTest
    @MethodSource("createExceptionParams")
    fun toResponse_whenException_shouldReturnExpectedMessageAndStatus(
        exception: Exception,
        expectedStatus: Status,
        expectedMessage: String,
    ) {
        val result = sut.toResponse(exception)

        Assertions.assertEquals(expectedMessage, result.entity)
        Assertions.assertEquals(expectedStatus.statusCode, result.status)
    }
}