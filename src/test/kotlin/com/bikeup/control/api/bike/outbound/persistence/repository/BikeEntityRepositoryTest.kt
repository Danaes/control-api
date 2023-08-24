package com.bikeup.control.api.bike.outbound.persistence.repository

import com.bikeup.control.api.bike.core.application.BikeCreateCmdMother
import com.bikeup.control.api.bike.outbound.persistence.entity.BikeEntity
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.bson.types.ObjectId
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class BikeEntityRepositoryTest {

    @Inject
    private lateinit var bikeEntityRepository: BikeEntityRepository

    @BeforeAll
    internal fun setUp() {
        bikeEntityRepository.persist(BIKE_ENTITY)
    }

    @Test
    @Order(1)
    internal fun find_whenUserId_shouldReturnHisBikes() {
        val result = bikeEntityRepository.find(USER_ID)

        assertTrue(result.isNotEmpty())
        assertEquals(BIKE_ENTITY, result[0])
    }

    @Test
    @Order(2)
    internal fun findByPK_whenIdAndUserId_shouldReturnBike() {
        val result = bikeEntityRepository.findByPK(BIKE_ID, USER_ID)

        assertEquals(BIKE_ENTITY, result)
    }

    @Test
    @Order(3)
    internal fun checkNotExists_whenId_shouldReturnTrueIfNotExists() {
        val result = bikeEntityRepository.checkNotExists(ObjectId().toString())

        assertTrue(result)
    }

    @Test
    @Order(4)
    internal fun deleteByIdAndUserId_whenIdAndUserId_shouldDeleteIt() {
        bikeEntityRepository.deleteByIdAndUserId(BIKE_ID, USER_ID)

        assertNull(bikeEntityRepository.findByPK(BIKE_ID, USER_ID))
    }

    private companion object {
        val USER_ID = ObjectId().toString()
        val BIKE_ENTITY = BikeEntity.create(BikeCreateCmdMother.of(userId = USER_ID))
        val BIKE_ID = BIKE_ENTITY.id
    }
}