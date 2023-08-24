package com.bikeup.control.api.bike.outbound.persistence.adapter

import com.bikeup.control.api.bike.core.application.EquipmentCreateCmdMother
import com.bikeup.control.api.bike.core.application.EquipmentUpdateCmdMother
import com.bikeup.control.api.bike.core.domain.exception.EquipmentNotFoundException
import com.bikeup.control.api.bike.outbound.persistence.entity.EquipmentEntity
import com.bikeup.control.api.bike.outbound.persistence.repository.EquipmentEntityRepository
import com.bikeup.control.api.common.ClockTestUtils
import org.bson.types.ObjectId
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.mockito.Mockito.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EquipmentRepositoryAdapterTest {

    private val bikeRepositoryAdapter = mock<BikeRepositoryAdapter>()
    private val equipmentEntityRepository = mock<EquipmentEntityRepository>()

    private lateinit var sut: EquipmentRepositoryAdapter

    @BeforeAll
    internal fun setUp() {
        ClockTestUtils.mockClock()
    }

    @BeforeEach
    internal fun init() {
        reset(bikeRepositoryAdapter)
        reset(equipmentEntityRepository)

        sut = EquipmentRepositoryAdapter(bikeRepositoryAdapter, equipmentEntityRepository)
    }

    @Test
    internal fun save_whenEquipmentCreateCmd_shouldReturnEquipment() {
        val equipmentCreateCmd = EquipmentCreateCmdMother.of(bikeId = BIKE_ID)
        val expected = EquipmentEntity.create(equipmentCreateCmd)

        val result = sut.save(equipmentCreateCmd)

        assertEquals(expected.copy(id = result.id).toDomain(), result)
        verify(equipmentEntityRepository).persist(expected.copy(id = result.id))
    }

    @Test
    internal fun findByBikeId_whenBikeId_shouldItsEquipments() {
        val expected = EQUIPMENT_ENTITY.toDomain()
        doReturn(listOf(EQUIPMENT_ENTITY)).`when`(equipmentEntityRepository).findByBikeId(BIKE_ID)

        val result = sut.findByBikeId(BIKE_ID)

        verify(equipmentEntityRepository).findByBikeId(BIKE_ID)
        assertTrue(result.isNotEmpty())
        assertEquals(expected, result[0])
    }

    @Test
    internal fun update_whenEquipmentUpdateCmd_shouldReturnEquipment() {
        val equipmentUpdateCmd = EquipmentUpdateCmdMother.of(id = EQUIPMENT_ID, bikeId = BIKE_ID)
        val expected = EQUIPMENT_ENTITY.update(equipmentUpdateCmd)
        doReturn(EQUIPMENT_ENTITY).`when`(equipmentEntityRepository).findByPK(equipmentUpdateCmd.id, BIKE_ID)

        val result = sut.update(equipmentUpdateCmd)

        verify(equipmentEntityRepository).findByPK(equipmentUpdateCmd.id, BIKE_ID)
        verify(equipmentEntityRepository).update(expected)
        assertEquals(expected.toDomain(), result)
    }

    @Test
    internal fun update_whenEquipmentNotExists_shouldThrowEquipmentNotFoundException() {
        val equipmentUpdateCmd = EquipmentUpdateCmdMother.of(bikeId = BIKE_ID)
        val expected = "Equipment with id ${equipmentUpdateCmd.id} not exists"
        doReturn(null).`when`(equipmentEntityRepository).findByPK(equipmentUpdateCmd.id, BIKE_ID)

        val exception = assertThrows<EquipmentNotFoundException> { sut.update(equipmentUpdateCmd) }

        verify(equipmentEntityRepository).findByPK(equipmentUpdateCmd.id, BIKE_ID)
        verify(equipmentEntityRepository, never()).update(expected)
        assertEquals(expected, exception.message!!)
    }

    @Test
    internal fun delete_whenEquipmentIdAndBikeId_shouldDeleteEquipment() {
        sut.delete(EQUIPMENT_ID, BIKE_ID)

        verify(equipmentEntityRepository).deleteByIdAndBikeId(EQUIPMENT_ID, BIKE_ID)
    }

    private companion object {
        val BIKE_ID = ObjectId().toString()
        val EQUIPMENT_ENTITY = EquipmentEntity.create(EquipmentCreateCmdMother.of(bikeId = BIKE_ID))
        val EQUIPMENT_ID = EQUIPMENT_ENTITY.id
    }
}