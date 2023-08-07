package com.bikeup.control.api.bike.core.application.usecase

import com.bikeup.control.api.bike.core.domain.valueobject.EquipmentType
import jakarta.ws.rs.BadRequestException

data class EquipmentCreateCmd(
    val bikeId: String?,
    val brand: String,
    val model: String,
    val distance: Double = 0.0,
    val type: EquipmentType
) {
    init {
        if (brand.isBlank()) throw BadRequestException("Brand parameter may not be blank")
        if (model.isBlank()) throw BadRequestException("Model parameter may not be blank")
        if (distance < 0) throw BadRequestException("Distance parameter may not be negative")
    }

    fun addBikeId(bikeId: String) = this.copy(bikeId = bikeId)
}
