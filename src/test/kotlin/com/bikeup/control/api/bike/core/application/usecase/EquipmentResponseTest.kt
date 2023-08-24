package com.bikeup.control.api.bike.core.application.usecase

import com.bikeup.control.api.bike.core.domain.EquipmentMother
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class EquipmentResponseTest {

    @Test
    internal fun map_whenBike_shouldReturnEquipmentResponse() {
        val equipment = EquipmentMother.of()

        val result = EquipmentResponse.map(equipment)

        Assertions.assertEquals(equipment.id, result.id)
        Assertions.assertEquals(equipment.brand, result.brand)
        Assertions.assertEquals(equipment.model, result.model)
        Assertions.assertEquals(equipment.distance, result.distance)
        Assertions.assertEquals(equipment.status, result.status)
        Assertions.assertEquals(equipment.type, result.type)
    }
}