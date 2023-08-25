package com.bikeup.control.api.bike.core.domain.model

import com.bikeup.control.api.bike.core.domain.EquipmentMother
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class EquipmentTest {

    @Test
    internal fun increaseDistance_whenDistance_shouldPlusNewDistance() {
        val equipment = EquipmentMother.of(distance = 70.00)
        val newDistance = 30.0

        val result = equipment.increaseDistance(newDistance)

        assertEquals(100.00, result.distance)
    }
}