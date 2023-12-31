package com.bikeup.control.api.bike.core.domain.model

import com.bikeup.control.api.bike.core.domain.valueobject.EquipmentStatus
import com.bikeup.control.api.bike.core.domain.valueobject.EquipmentType

data class Equipment(
    val id: String,
    val bikeId: String,
    val brand: String,
    val model: String,
    val distance: Double,
    val status: EquipmentStatus,
    val type: EquipmentType
) {
    fun increaseDistance(distance: Double) = this.copy(distance = this.distance.plus(distance))
}
