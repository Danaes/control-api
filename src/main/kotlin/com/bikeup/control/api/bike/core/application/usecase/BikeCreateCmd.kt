package com.bikeup.control.api.bike.core.application.usecase

import jakarta.ws.rs.BadRequestException

data class BikeCreateCmd(
    val userId: String?,
    val brand: String,
    val model: String,
    val year: Int,
    val distance: Double = 0.0
) {
    init {
        if (brand.isBlank()) throw BadRequestException("Brand parameter may not be blank")
        if (model.isBlank()) throw BadRequestException("Model parameter may not be blank")
        if (year < 0) throw BadRequestException("Year parameter is invalid")
        if (distance < 0) throw BadRequestException("Distance parameter may not be negative")
    }

    fun addUserId(userId: String) = this.copy(userId = userId)
}
