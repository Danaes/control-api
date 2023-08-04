package com.bikeup.control.api.bike.core.domain.model

import com.bikeup.control.api.bike.core.domain.valueobject.EquipmentStatus

data class Equipment(
    val id: String,
    val brand: String,
    val model: String,
    val distance: Double,
    val createAt: String,
    val status: EquipmentStatus
) {
    fun increaseDistance(distance: Double) = this.copy(distance = this.distance.plus(distance))
}
