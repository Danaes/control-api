package com.bikeup.control.api.bike.core.application.usecase

import com.bikeup.control.api.bike.core.application.BikeCreateCmdMother
import jakarta.ws.rs.BadRequestException
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BikeCreateCmdTest {

    @Test
    internal fun init_whenParamsAreCorrect_shouldThrowNothing() {
        assertDoesNotThrow { BikeCreateCmdMother.of() }
    }

    @Test
    internal fun init_whenBrandIsBlank_shouldThrowBadRequestException() {
        val exception = assertThrows<BadRequestException> { BikeCreateCmdMother.of(brand = "") }

        assertEquals("Brand parameter may not be blank", exception.message!!)
    }

    @Test
    internal fun init_whenModelIsBlank_shouldThrowBadRequestException() {
        val exception = assertThrows<BadRequestException> { BikeCreateCmdMother.of(model = "") }

        assertEquals("Model parameter may not be blank", exception.message!!)
    }

    @Test
    internal fun init_whenYearIsBlank_shouldThrowBadRequestException() {
        val exception = assertThrows<BadRequestException> { BikeCreateCmdMother.of(year = -1) }

        assertEquals("Year parameter is invalid", exception.message!!)
    }

    @Test
    internal fun init_whenDistanceIsBlank_shouldThrowBadRequestException() {
        val exception = assertThrows<BadRequestException> { BikeCreateCmdMother.of(distance = -1.0) }

        assertEquals("Distance parameter may not be negative", exception.message!!)
    }

    @Test
    internal fun addUserId_whenUserId_shouldBikeCreateCmdWithUserId() {
        val bikeCreateCmd = BikeCreateCmdMother.of(userId = null)
        val userId = ObjectId().toString()

        val result = bikeCreateCmd.addUserId(userId)

        assertEquals(userId, result.userId)
    }
}