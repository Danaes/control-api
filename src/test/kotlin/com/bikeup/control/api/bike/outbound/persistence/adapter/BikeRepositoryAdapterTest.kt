package com.bikeup.control.api.bike.outbound.persistence.adapter

import com.bikeup.control.api.authentication.outbound.persistence.adapter.UserRepositoryAdapter
import com.bikeup.control.api.bike.core.application.BikeCreateCmdMother
import com.bikeup.control.api.bike.core.application.BikeUpdateCmdMother
import com.bikeup.control.api.bike.core.domain.exception.BikeNotFoundException
import com.bikeup.control.api.bike.core.domain.model.Equipment
import com.bikeup.control.api.bike.outbound.persistence.entity.BikeEntity
import com.bikeup.control.api.bike.outbound.persistence.repository.BikeEntityRepository
import com.bikeup.control.api.common.ClockTestUtils
import org.bson.types.ObjectId
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mockito.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BikeRepositoryAdapterTest {

    private val bikeEntityRepository = mock<BikeEntityRepository>()
    private val userRepositoryAdapter = mock<UserRepositoryAdapter>()
    private val equipmentRepositoryAdapter = mock<EquipmentRepositoryAdapter>()

    private lateinit var sut: BikeRepositoryAdapter

    @BeforeAll
    internal fun setUp() {
        ClockTestUtils.mockClock()
    }

    @BeforeEach
    internal fun init() {
        reset(bikeEntityRepository)
        reset(userRepositoryAdapter)
        reset(equipmentRepositoryAdapter)

        `when`(bikeEntityRepository.findByPK(BIKE_ID, USER_ID)).thenReturn(BIKE_ENTITY)
        `when`(equipmentRepositoryAdapter.findByBikeId(BIKE_ID)).thenReturn(EQUIPMENTS)

        sut = BikeRepositoryAdapter(bikeEntityRepository, userRepositoryAdapter, equipmentRepositoryAdapter)
    }

    @Test
    internal fun save_whenBikeCreateCmd_shouldPersistBikeEntity() {
        val bikeCreateCmd = BikeCreateCmdMother.of(userId = USER_ID)
        val expected = BikeEntity.create(bikeCreateCmd)

        val result = sut.save(bikeCreateCmd)

        verify(bikeEntityRepository).persist(expected.copy(id = result.id))
        assertEquals(expected.copy(id = result.id).toDomain(), result)
    }

    @Test
    internal fun update_whenBikeUpdateCmd_shouldUpdateBikeEntity() {
        val bikeUpdateCmd = BikeUpdateCmdMother.of(id = BIKE_ID, userId = USER_ID, brand = "Trek")
        val expected = BIKE_ENTITY.update(bikeUpdateCmd)

        val result = sut.update(bikeUpdateCmd)

        verify(bikeEntityRepository).update(expected)
        assertEquals(expected.toDomain(EQUIPMENTS), result)
    }

    @Test
    internal fun find_whenUserId_shouldReturnBikes() {
        val otherBike = BIKE_ENTITY.copy(id = ObjectId().toString(), userId = USER_ID)
        val bikeEntities = listOf(BIKE_ENTITY, otherBike)
        `when`(bikeEntityRepository.find(USER_ID)).thenReturn(bikeEntities)

        val result = sut.find(USER_ID)

        assertEquals(BIKE_ENTITY.toDomain(EQUIPMENTS), result[0])
        assertEquals(otherBike.toDomain(EQUIPMENTS), result[1])
    }

    @Test
    internal fun find_whenBikeNotFound_shouldThrowBikeNotFoundException() {
        reset(bikeEntityRepository)
        `when`(bikeEntityRepository.findByPK(USER_ID, BIKE_ID)).thenReturn(null)

        val exception = assertThrows<BikeNotFoundException> { sut.find(USER_ID, BIKE_ID) }

        assertEquals("Bike with id $BIKE_ID not exists", exception.message!!)
    }

    @Test
    internal fun find_whenBikeExists_shouldReturnIt() {
        val expected = BIKE_ENTITY.toDomain(EQUIPMENTS)

        val result = sut.find(USER_ID, BIKE_ID)

        assertEquals(expected, result)
    }

    @Test
    internal fun delete_whenUserIdAndBikeId_shouldDeleteBike() {
        sut.delete(USER_ID, BIKE_ID)

        verify(bikeEntityRepository).deleteByIdAndUserId(BIKE_ID, USER_ID)
    }

    @Test
    internal fun checkExists_whenUserIdIsNull_shouldThrowIllegalStateException() {
        val exception = assertThrows<IllegalStateException> { sut.checkExists(null) }

        assertEquals("BikeId must exist", exception.message!!)
    }

    @Test
    internal fun checkExists_whenUserIdNotExists_shouldThrowBikeNotFoundException() {
        val bikeId = "bikeId"
        `when`(bikeEntityRepository.checkNotExists(bikeId)).thenReturn(true)

        val exception = assertThrows<BikeNotFoundException> { sut.checkExists(bikeId) }

        assertEquals("Bike with id $bikeId not exists", exception.message!!)
    }

    @Test
    internal fun checkExists_whenUserIdExists_shouldDoNothing() {
        val bikeId = "bikeId"
        `when`(bikeEntityRepository.checkNotExists(bikeId)).thenReturn(false)

        assertDoesNotThrow { sut.checkExists(bikeId) }
    }

    private companion object {
        val BIKE_ENTITY = BikeEntity.create(BikeCreateCmdMother.of(userId = ObjectId().toString()))
        val USER_ID = BIKE_ENTITY.userId
        val BIKE_ID = BIKE_ENTITY.id
        val EQUIPMENTS = emptyList<Equipment>()
    }
}