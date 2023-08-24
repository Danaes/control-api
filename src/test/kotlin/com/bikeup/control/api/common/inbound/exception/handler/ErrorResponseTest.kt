package com.bikeup.control.api.common.inbound.exception.handler

import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.Response.Status
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ErrorResponseTest {

    @Test
    internal fun toResponse_whenMethodIsInvoked_shouldReturnResponseWithCorrectStatus() {
        val sut = ErrorResponse("message", Status.NOT_FOUND)
        val expected = Response.ok("message").status(Status.NOT_FOUND).build()

        val result = sut.toResponse()

        assertEquals(expected.entity, result.entity)
        assertEquals(expected.status, result.status)
    }

    @Test
    internal fun toString_whenMethodIsInvoked_shouldReturnCustomString() {
        val expectedMessage = "message"
        val expectedStatus = Status.NOT_FOUND
        val sut = ErrorResponse(expectedMessage, expectedStatus)

        val result = sut.toString()

        assertEquals("$expectedMessage - HTTP Status Code: $expectedStatus", result)
    }
}