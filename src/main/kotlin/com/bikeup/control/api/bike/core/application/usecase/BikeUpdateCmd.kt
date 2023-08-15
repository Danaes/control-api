package com.bikeup.control.api.bike.core.application.usecase

import com.bikeup.control.api.bike.core.domain.model.Bike
import jakarta.ws.rs.BadRequestException

data class BikeUpdateCmd(
    val id: String,
    val userId: String?,
    val brand: String? = null,
    val model: String? = null,
    val year: Int? = null,
    val distance: Double? = null,
) {
    init {
        if (id.isBlank()) throw BadRequestException("Id parameter may not be blank")
    }

    fun addUserId(userId: String) = this.copy(userId = userId)

    companion object {
        fun map(bike: Bike): BikeUpdateCmd =
            BikeUpdateCmd(
                id = bike.id,
                userId = bike.userId,
                brand = bike.brand,
                model = bike.model,
                year = bike.year,
                distance = bike.distance,
            )
    }
}
