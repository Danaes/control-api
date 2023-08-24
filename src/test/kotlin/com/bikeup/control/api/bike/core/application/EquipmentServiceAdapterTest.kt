package com.bikeup.control.api.bike.core.application

import com.bikeup.control.api.bike.core.application.port.output.persistence.EquipmentRepositoryPort
import com.bikeup.control.api.bike.core.application.usecase.EquipmentResponse
import com.bikeup.control.api.bike.core.domain.EquipmentMother
import com.bikeup.control.api.bike.core.domain.valueobject.EquipmentType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class EquipmentServiceAdapterTest {

    private val equipmentRepositoryPort = mock<EquipmentRepositoryPort>()
    private lateinit var sut: EquipmentServiceAdapter

    @BeforeEach
    internal fun init() {
        reset(equipmentRepositoryPort)

        sut = EquipmentServiceAdapter(equipmentRepositoryPort)
    }

    @Test
    internal fun save_whenEquipmentCreateCmd_shouldReturnEquipmentResponse() {
        val equipmentCreateCmd = EquipmentCreateCmdMother.of()
        val equipment = EquipmentMother.of(
            brand = equipmentCreateCmd.brand,
            model = equipmentCreateCmd.model,
            distance = equipmentCreateCmd.distance,
            type = equipmentCreateCmd.type,
        )
        val expected = EquipmentResponse.map(equipment)
        `when`(equipmentRepositoryPort.save(equipmentCreateCmd)).thenReturn(equipment)

        val result = sut.save(equipmentCreateCmd)

        assertEquals(expected, result)
        verify(equipmentRepositoryPort).save(equipmentCreateCmd)
    }

    @Test
    internal fun update_whenEquipmentUpdateCmd_shouldReturnEquipmentResponse() {
        val equipmentUpdateCmd = EquipmentUpdateCmdMother.of(
            brand = "SRAM", model = "GX", distance = 0.0, type = EquipmentType.ELECTRONIC_GEAR_SHIFTING_SYSTEM
        )
        val equipment = EquipmentMother.of(
            brand = equipmentUpdateCmd.brand!!,
            model = equipmentUpdateCmd.model!!,
            distance = equipmentUpdateCmd.distance!!,
            type = equipmentUpdateCmd.type!!,
        )
        val expected = EquipmentResponse.map(equipment)
        `when`(equipmentRepositoryPort.update(equipmentUpdateCmd)).thenReturn(equipment)

        val result = sut.update(equipmentUpdateCmd)

        assertEquals(expected, result)
        verify(equipmentRepositoryPort).update(equipmentUpdateCmd)
    }

    @Test
    internal fun delete_whenBikeIdAndEquipmentId_shouldDeleteEquipment() {
        val bikeId = "bikeId"
        val equipmentId = "equipmentId"

        sut.delete(bikeId, equipmentId)

        verify(equipmentRepositoryPort).delete(equipmentId, bikeId)
    }
}