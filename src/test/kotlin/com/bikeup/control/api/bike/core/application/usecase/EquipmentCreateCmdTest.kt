package com.bikeup.control.api.bike.core.application.usecase

import com.bikeup.control.api.bike.core.application.EquipmentCreateCmdMother
import jakarta.ws.rs.BadRequestException
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class EquipmentCreateCmdTest {

    @Test
    internal fun init_whenParamsAreCorrect_shouldThrowNothing() {
        assertDoesNotThrow { EquipmentCreateCmdMother.of() }
    }

    @Test
    internal fun init_whenBrandIsBlank_shouldThrowBadRequestException() {
        val exception = assertThrows<BadRequestException> { EquipmentCreateCmdMother.of(brand = "") }

        Assertions.assertEquals("Brand parameter may not be blank", exception.message!!)
    }

    @Test
    internal fun init_whenModelIsBlank_shouldThrowBadRequestException() {
        val exception = assertThrows<BadRequestException> { EquipmentCreateCmdMother.of(model = "") }

        Assertions.assertEquals("Model parameter may not be blank", exception.message!!)
    }

    @Test
    internal fun init_whenDistanceIsBlank_shouldThrowBadRequestException() {
        val exception = assertThrows<BadRequestException> { EquipmentCreateCmdMother.of(distance = -1.0) }

        Assertions.assertEquals("Distance parameter may not be negative", exception.message!!)
    }

    @Test
    internal fun addBikeId_whenUserId_shouldBikeCreateCmdWithUserId() {
        val equipmentCreateCmd = EquipmentCreateCmdMother.of(bikeId = null)
        val bikeId = ObjectId().toString()

        val result = equipmentCreateCmd.addBikeId(bikeId)

        Assertions.assertEquals(bikeId, result.bikeId)
    }
}