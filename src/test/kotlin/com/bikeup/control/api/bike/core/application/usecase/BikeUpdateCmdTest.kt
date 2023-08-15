package com.bikeup.control.api.bike.core.application.usecase

import com.bikeup.control.api.bike.core.application.BikeUpdateCmdMother
import com.bikeup.control.api.bike.core.domain.BikeMother
import jakarta.ws.rs.BadRequestException
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class BikeUpdateCmdTest {

    @Test
    internal fun init_whenParamsAreCorrect_shouldThrowNothing() {
        assertDoesNotThrow { BikeUpdateCmdMother.of() }
    }

    @Test
    internal fun init_whenIdIsBlank_shouldThrowBadRequestException() {
        val exception = assertThrows<BadRequestException> { BikeUpdateCmdMother.of(id = "") }

        Assertions.assertEquals("Id parameter may not be blank", exception.message!!)
    }

    @Test
    internal fun addUserId_whenUserId_shouldBikeUpdateCmdWithUserId() {
        val bikeUpdateCmdM = BikeUpdateCmdMother.of(userId = null)
        val userId = ObjectId().toString()

        val result = bikeUpdateCmdM.addUserId(userId)

        Assertions.assertEquals(userId, result.userId)
    }

    @Test
    internal fun map_whenBike_shouldReturnBikeUpdateCmd() {
        val bike = BikeMother.of()

        val result = BikeUpdateCmd.map(bike)

        Assertions.assertEquals(bike.id, result.id)
        Assertions.assertEquals(bike.userId, result.userId)
        Assertions.assertEquals(bike.brand, result.brand)
        Assertions.assertEquals(bike.model, result.model)
        Assertions.assertEquals(bike.year, result.year)
        Assertions.assertEquals(bike.distance, result.distance)
    }
}