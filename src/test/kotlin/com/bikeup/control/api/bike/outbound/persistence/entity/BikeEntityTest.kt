package com.bikeup.control.api.bike.outbound.persistence.entity

import com.bikeup.control.api.bike.core.application.BikeCreateCmdMother
import com.bikeup.control.api.bike.core.application.BikeUpdateCmdMother
import com.bikeup.control.api.bike.core.application.usecase.BikeUpdateCmd
import com.bikeup.control.api.bike.core.domain.model.Bike
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BikeEntityTest {

    private fun createBikeUpdateCmdParams() = listOf(
        Arguments.of(BikeUpdateCmdMother.of(id = SUT.id, brand = "Trek")),
        Arguments.of(BikeUpdateCmdMother.of(id = SUT.id, model = "SuperCaliber")),
        Arguments.of(BikeUpdateCmdMother.of(id = SUT.id, year = 2024)),
        Arguments.of(BikeUpdateCmdMother.of(id = SUT.id, distance = 100.0)),
    )

    @ParameterizedTest
    @MethodSource("createBikeUpdateCmdParams")
    internal fun update_whenBikeUpdateCmd_shouldUpdateBikeEntity(
        bikeUpdateCmd: BikeUpdateCmd
    ) {
        val oldBikeEntity = SUT

        val result = oldBikeEntity.update(bikeUpdateCmd)

        assertNotEquals(oldBikeEntity, result)
    }

    @Test
    internal fun toDomain_whenMethodIsInvoked_shouldReturnBike() {
        val expected = Bike(
            id = SUT.id,
            userId = SUT.userId,
            brand = SUT.brand,
            model = SUT.model,
            year = SUT.year,
            distance = SUT.distance,
            equipments = emptyList()
        )

        val result = SUT.toDomain()

        assertEquals(expected, result)
    }

    @Test
    internal fun create_whenBikeCreateCmd_shouldReturnBikeEntity() {
        val result = BikeEntity.create(BIKE_CREATE_CMD)

        assertNotNull(result.id)
        assertEquals(BIKE_CREATE_CMD.userId, result.userId)
        assertEquals(BIKE_CREATE_CMD.brand, result.brand)
        assertEquals(BIKE_CREATE_CMD.model, result.model)
        assertEquals(BIKE_CREATE_CMD.year, result.year)
        assertEquals(BIKE_CREATE_CMD.distance, result.distance)
        assertNotNull(result.createdAt)
        assertNull(result.updatedAt)
    }

    private companion object {
        val BIKE_CREATE_CMD = BikeCreateCmdMother.of(userId = ObjectId().toString())
        val SUT = BikeEntity.create(BIKE_CREATE_CMD)
    }
}