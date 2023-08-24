package com.bikeup.control.api.bike.core.application.usecase

import com.bikeup.control.api.bike.core.domain.model.Equipment
import com.bikeup.control.api.bike.core.domain.valueobject.EquipmentStatus
import com.bikeup.control.api.bike.core.domain.valueobject.EquipmentType

data class EquipmentResponse(
    val id: String,
    val brand: String,
    val model: String,
    val distance: Double,
    val status: EquipmentStatus,
    val type: EquipmentType
) {
    companion object {
        fun map(equipment: Equipment) = EquipmentResponse(
            id = equipment.id,
            brand = equipment.brand,
            model = equipment.model,
            distance = equipment.distance,
            status = equipment.status,
            type = equipment.type
        )
    }
}
