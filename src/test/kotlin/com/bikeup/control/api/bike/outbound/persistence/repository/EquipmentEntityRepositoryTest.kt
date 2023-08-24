package com.bikeup.control.api.bike.outbound.persistence.repository

import com.bikeup.control.api.bike.core.application.EquipmentCreateCmdMother
import com.bikeup.control.api.bike.outbound.persistence.entity.EquipmentEntity
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.bson.types.ObjectId
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class EquipmentEntityRepositoryTest {

    @Inject
    private lateinit var equipmentEntityRepository: EquipmentEntityRepository

    @BeforeAll
    internal fun setUp() {
        equipmentEntityRepository.persist(EQUIPMENT_ENTITY)
    }

    @Test
    @Order(1)
    internal fun findByBikeId_whenBikeId_shouldReturnItsEquipments() {
        val result = equipmentEntityRepository.findByBikeId(BIKE_ID)

        assertTrue(result.isNotEmpty())
        assertEquals(EQUIPMENT_ENTITY, result[0])
    }

    @Test
    @Order(2)
    internal fun findByPK_whenIdAndBikeId_shouldReturnEquipment() {
        val result = equipmentEntityRepository.findByPK(EQUIPMENT_ID, BIKE_ID)

        assertEquals(EQUIPMENT_ENTITY, result)
    }

    @Test
    @Order(3)
    internal fun deleteByIdAndBikeId_whenIdAndBikeId_shouldDeleteIt() {
        equipmentEntityRepository.deleteByIdAndBikeId(EQUIPMENT_ID, BIKE_ID)

        assertNull(equipmentEntityRepository.findByPK(EQUIPMENT_ID, BIKE_ID))
    }

    private companion object {
        val BIKE_ID = ObjectId().toString()
        val EQUIPMENT_ENTITY = EquipmentEntity.create(EquipmentCreateCmdMother.of(bikeId = BIKE_ID))
        val EQUIPMENT_ID = EQUIPMENT_ENTITY.id
    }
}