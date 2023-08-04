package com.bikeup.control.api.bike.core.domain.model

data class Bike(
    val id: String,
    val userId: String,
    val brand: String,
    val model: String,
    val year: Int,
    val distance: Double,
    val equipments: List<Equipment>
) {
    fun increaseDistance(distance: Double): Bike {
        val distanceUpdated = this.distance.plus(distance)
        val equipmentsUpdated = this.equipments.map { it.increaseDistance(distance) }
        return this.copy(distance = distanceUpdated, equipments = equipmentsUpdated)
    }
}
