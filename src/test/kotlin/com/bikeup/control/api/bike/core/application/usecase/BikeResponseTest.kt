package com.bikeup.control.api.bike.core.application.usecase

import com.bikeup.control.api.bike.core.domain.BikeMother
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BikeResponseTest {

    @Test
    internal fun map_whenBike_shouldReturnBikeResponse() {
        val bike = BikeMother.of()

        val result = BikeResponse.map(bike)

        assertEquals(bike.id, result.id)
        assertEquals(bike.brand, result.brand)
        assertEquals(bike.model, result.model)
        assertEquals(bike.year, result.year)
        assertEquals(bike.distance, result.distance)
        assertEquals(bike.equipments.size, result.equipments.size)
    }
}