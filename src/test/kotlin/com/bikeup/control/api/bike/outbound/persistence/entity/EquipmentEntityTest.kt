package com.bikeup.control.api.bike.outbound.persistence.entity

import com.bikeup.control.api.bike.core.application.EquipmentCreateCmdMother
import com.bikeup.control.api.bike.core.application.EquipmentUpdateCmdMother
import com.bikeup.control.api.bike.core.application.usecase.EquipmentUpdateCmd
import com.bikeup.control.api.bike.core.domain.model.Equipment
import com.bikeup.control.api.bike.core.domain.valueobject.EquipmentStatus
import com.bikeup.control.api.bike.core.domain.valueobject.EquipmentType
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EquipmentEntityTest {

    private fun createEquipmentUpdateCmdParams() = listOf(
        Arguments.of(EquipmentUpdateCmdMother.of(id = SUT.id, brand = "Shimano")),
        Arguments.of(EquipmentUpdateCmdMother.of(id = SUT.id, model = "XTR")),
        Arguments.of(EquipmentUpdateCmdMother.of(id = SUT.id, distance = 100.0)),
        Arguments.of(EquipmentUpdateCmdMother.of(id = SUT.id, type = EquipmentType.BOTTOM_BRACKET)),
    )

    @ParameterizedTest
    @MethodSource("createEquipmentUpdateCmdParams")
    internal fun update_whenBikeUpdateCmd_shouldUpdateBikeEntity(
        equipmentUpdateCmd: EquipmentUpdateCmd
    ) {
        val oldBikeEntity = SUT

        val result = oldBikeEntity.update(equipmentUpdateCmd)

        assertNotEquals(oldBikeEntity, result)
    }

    @Test
    internal fun toDomain_whenMethodIsInvoked_shouldReturnBike() {
        val expected = Equipment(
            id = SUT.id,
            bikeId = SUT.bikeId,
            brand = SUT.brand,
            model = SUT.model,
            distance = SUT.distance,
            status = SUT.status,
            type = SUT.type,
        )

        val result = SUT.toDomain()

        assertEquals(expected, result)
    }

    @Test
    internal fun create_whenBikeCreateCmd_shouldReturnBikeEntity() {
        val result = EquipmentEntity.create(EQUIPMENT_CREATE_CMD)

        assertNotNull(result.id)
        assertEquals(EQUIPMENT_CREATE_CMD.bikeId, result.bikeId)
        assertEquals(EQUIPMENT_CREATE_CMD.brand, result.brand)
        assertEquals(EQUIPMENT_CREATE_CMD.model, result.model)
        assertEquals(EQUIPMENT_CREATE_CMD.type, result.type)
        assertEquals(EQUIPMENT_CREATE_CMD.distance, result.distance)
        assertEquals(EquipmentStatus.ACTIVE, result.status)
        assertNotNull(result.createdAt)
        assertNull(result.updatedAt)
    }

    private companion object {
        val EQUIPMENT_CREATE_CMD = EquipmentCreateCmdMother.of(bikeId = ObjectId().toString())
        val SUT = EquipmentEntity.create(EQUIPMENT_CREATE_CMD)
    }

}