package com.bikeup.control.api.bike.inbound.exception.handler

import com.bikeup.control.api.bike.core.domain.exception.BikeException
import com.bikeup.control.api.bike.core.domain.exception.BikeNotFoundException
import com.bikeup.control.api.bike.core.domain.exception.EquipmentNotFoundException
import jakarta.ws.rs.core.Response.Status
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BikeExceptionHandlerTest {

    private val sut = BikeExceptionHandler()

    private fun createBikeExceptionParams() = listOf(
        Arguments.of(BikeNotFoundException("Bike not exists"), Status.NOT_FOUND),
        Arguments.of(EquipmentNotFoundException("Equipment not exists"), Status.NOT_FOUND),
        Arguments.of(OtherBikeException("Internal server error"), Status.INTERNAL_SERVER_ERROR),
    )

    @ParameterizedTest
    @MethodSource("createBikeExceptionParams")
    fun toResponse_whenAuthenticationException_shouldReturnExpectedMessageAndStatus(
        exception: BikeException,
        expectedStatus: Status
    ) {
        val result = sut.toResponse(exception)

        assertEquals(exception.message!!, result.entity)
        assertEquals(expectedStatus.statusCode, result.status)
    }

    class OtherBikeException(message: String) : BikeException(message)
}