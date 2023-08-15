package com.bikeup.control.api.bike.core.application.usecase

import com.bikeup.control.api.bike.core.application.EquipmentUpdateCmdMother
import com.bikeup.control.api.bike.core.domain.EquipmentMother
import jakarta.ws.rs.BadRequestException
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class EquipmentUpdateCmdTest {

    @Test
    internal fun init_whenParamsAreCorrect_shouldThrowNothing() {
        assertDoesNotThrow { EquipmentUpdateCmdMother.of() }
    }

    @Test
    internal fun init_whenIdIsBlank_shouldThrowBadRequestException() {
        val exception = assertThrows<BadRequestException> { EquipmentUpdateCmdMother.of(id = "") }

        Assertions.assertEquals("Id parameter may not be blank", exception.message!!)
    }

    @Test
    internal fun addBikeId_whenUserId_shouldBikeUpdateCmdWithUserId() {
        val equipmentUpdateCmd = EquipmentUpdateCmdMother.of(bikeId = null)
        val bikeId = ObjectId().toString()

        val result = equipmentUpdateCmd.addBikeId(bikeId)

        Assertions.assertEquals(bikeId, result.bikeId)
    }

    @Test
    internal fun map_whenBike_shouldReturnBikeUpdateCmd() {
        val equipment = EquipmentMother.of()

        val result = EquipmentUpdateCmd.map(equipment)

        Assertions.assertEquals(equipment.id, result.id)
        Assertions.assertEquals(equipment.bikeId, result.bikeId)
        Assertions.assertEquals(equipment.brand, result.brand)
        Assertions.assertEquals(equipment.model, result.model)
        Assertions.assertEquals(equipment.distance, result.distance)
        Assertions.assertEquals(equipment.type, result.type)
    }

}