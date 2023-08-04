package com.bikeup.control.api.bike.core.application.usecase

import com.bikeup.control.api.bike.core.domain.model.Equipment
import com.bikeup.control.api.bike.core.domain.valueobject.EquipmentStatus
import java.time.Instant

data class EquipmentResponse(
    val brand: String,
    val model: String,
    val distance: Double,
    val createAt: Long,
    val status: EquipmentStatus
) {
    companion object {
        fun build(equipment: Equipment) = EquipmentResponse(
            brand = equipment.brand,
            model = equipment.model,
            distance = equipment.distance,
            createAt = Instant.parse(equipment.createAt).toEpochMilli(),
            status = equipment.status
        )
    }
}
