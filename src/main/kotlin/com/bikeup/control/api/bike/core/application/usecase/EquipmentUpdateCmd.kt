package com.bikeup.control.api.bike.core.application.usecase

import com.bikeup.control.api.bike.core.domain.model.Equipment
import com.bikeup.control.api.bike.core.domain.valueobject.EquipmentType
import jakarta.ws.rs.BadRequestException

data class EquipmentUpdateCmd(
    val id: String,
    val bikeId: String?,
    val brand: String?,
    val model: String?,
    val distance: Double?,
    val type: EquipmentType?
) {
    init {
        if (id.isBlank()) throw BadRequestException("Id parameter may not be blank")
    }

    fun addBikeId(bikeId: String) = this.copy(bikeId = bikeId)

    companion object {
        fun map(equipment: Equipment): EquipmentUpdateCmd =
            EquipmentUpdateCmd(
                id = equipment.id,
                bikeId = equipment.bikeId,
                brand = equipment.brand,
                model = equipment.model,
                distance = equipment.distance,
                type = equipment.type
            )
    }
}
