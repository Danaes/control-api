package com.bikeup.control.api.bike.core.application

import com.bikeup.control.api.bike.core.application.port.output.persistence.BikeRepositoryPort
import com.bikeup.control.api.bike.core.application.port.output.persistence.EquipmentRepositoryPort
import com.bikeup.control.api.bike.core.application.usecase.BikeResponse
import com.bikeup.control.api.bike.core.application.usecase.EquipmentUpdateCmd
import com.bikeup.control.api.bike.core.domain.BikeMother
import com.bikeup.control.api.bike.core.domain.EquipmentMother
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class BikeServiceAdapterTest {

    private val bikeRepositoryPort = mock<BikeRepositoryPort>()
    private val equipmentRepositoryPort = mock<EquipmentRepositoryPort>()

    private lateinit var sut: BikeServiceAdapter

    @BeforeEach
    internal fun init() {
        reset(bikeRepositoryPort)
        reset(equipmentRepositoryPort)

        sut = BikeServiceAdapter(bikeRepositoryPort, equipmentRepositoryPort)
    }

    @Test
    internal fun save_whenBikeCreateCmd_shouldReturnBikeResponse() {
        val expected = BikeResponse.map(BIKE)
        val bikeCreateCmd = BikeCreateCmdMother.of(
            userId = BIKE.userId,
            brand = BIKE.brand,
            model = BIKE.model,
            year = BIKE.year,
            distance = BIKE.distance
        )
        `when`(bikeRepositoryPort.save(bikeCreateCmd)).thenReturn(BIKE)

        val result = sut.save(bikeCreateCmd)

        assertEquals(expected, result)
        verify(bikeRepositoryPort).save(bikeCreateCmd)
    }

    @Test
    internal fun update_whenBikeUpdateCmd_shouldReturnBikeResponse() {
        val expected = BikeResponse.map(BIKE)
        val bikeUpdateCmd = BikeUpdateCmdMother.of(
            id = BIKE.id,
            userId = BIKE.userId,
            brand = BIKE.brand,
            model = BIKE.model,
            year = BIKE.year,
            distance = BIKE.distance
        )
        `when`(bikeRepositoryPort.update(bikeUpdateCmd)).thenReturn(BIKE)

        val result = sut.update(bikeUpdateCmd)

        assertEquals(expected, result)
        verify(bikeRepositoryPort).update(bikeUpdateCmd)
    }

    @Test
    internal fun find_whenUserId_shouldReturnBikeResponseList() {
        val secondBike = BIKE.copy(id = ObjectId().toString())
        val expectedFirst = BikeResponse.map(BIKE)
        val expectedSecond = BikeResponse.map(secondBike)
        `when`(bikeRepositoryPort.find(USER_ID)).thenReturn(listOf(BIKE, secondBike))

        val result = sut.find(USER_ID)

        assertEquals(expectedFirst, result[0])
        assertEquals(expectedSecond, result[1])
        verify(bikeRepositoryPort).find(USER_ID)
    }

    @Test
    internal fun find_whenUserIdAndBikeId_shouldReturnBikeResponse() {
        val expected = BikeResponse.map(BIKE)
        `when`(bikeRepositoryPort.find(USER_ID, BIKE_ID)).thenReturn(BIKE)

        val result = sut.find(USER_ID, BIKE_ID)

        assertEquals(expected, result)
        verify(bikeRepositoryPort).find(USER_ID, BIKE_ID)
    }

    @Test
    internal fun delete_whenUserIdAndBikeId_shouldDeleteIt() {
        sut.delete(USER_ID, BIKE_ID)

        verify(bikeRepositoryPort).delete(USER_ID, BIKE_ID)
    }

    @Test
    internal fun increaseDistance_whenDistance_shouldReturnBikeResponseWithMoreDistance() {
        val equipment = EquipmentMother.of()
        val newDistance = 100.0
        val bikeUpdateCmd = BikeUpdateCmdMother.of(
            id = BIKE.id,
            userId = BIKE.userId,
            brand = BIKE.brand,
            model = BIKE.model,
            year = BIKE.year,
            distance = BIKE.distance.plus(newDistance)
        )
        val updatedEquipment = equipment.copy(distance = equipment.distance.plus(newDistance))
        val updatedBike = BIKE.copy(distance = BIKE.distance.plus(newDistance), equipments = listOf(updatedEquipment))
        val expected = BikeResponse.map(updatedBike)
        `when`(bikeRepositoryPort.find(USER_ID, BIKE_ID)).thenReturn(BIKE.copy(equipments = listOf(equipment)))
        `when`(equipmentRepositoryPort.update(EquipmentUpdateCmd.map(updatedEquipment))).thenReturn(updatedEquipment)
        `when`(bikeRepositoryPort.update(bikeUpdateCmd)).thenReturn(updatedBike)

        val result = sut.increaseDistance(USER_ID, BIKE_ID, newDistance)

        assertEquals(expected, result)
    }

    private companion object {
        val USER_ID = ObjectId().toString()
        val BIKE_ID = ObjectId().toString()
        val BIKE = BikeMother.of(id = BIKE_ID, userId = USER_ID)
    }
}