package com.bikeup.control.api.bike.core.application.usecase

import com.bikeup.control.api.bike.core.domain.model.Bike

data class BikeResponse(
    val id: String,
    val brand: String,
    val model: String,
    val year: Int,
    val distance: Double,
    val equipments: List<EquipmentResponse>
) {
    companion object {
        fun map(bike: Bike): BikeResponse =
            BikeResponse(
                id = bike.id,
                brand = bike.brand,
                model = bike.model,
                year = bike.year,
                distance = bike.distance,
                equipments = bike.equipments.map { EquipmentResponse.map(it) },
            )
    }
}
