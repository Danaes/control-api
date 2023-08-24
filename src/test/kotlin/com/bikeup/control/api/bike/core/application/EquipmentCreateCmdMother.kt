package com.bikeup.control.api.bike.core.application

import com.bikeup.control.api.bike.core.application.usecase.EquipmentCreateCmd
import com.bikeup.control.api.bike.core.domain.valueobject.EquipmentType

object EquipmentCreateCmdMother {

    fun of(
        bikeId: String? = null,
        brand: String = "SRAM",
        model: String = "GX AXS",
        distance: Double = 0.0,
        type: EquipmentType = EquipmentType.ELECTRONIC_GEAR_SHIFTING_SYSTEM
    ): EquipmentCreateCmd =
        EquipmentCreateCmd(
            bikeId = bikeId,
            brand = brand,
            model = model,
            distance = distance,
            type = type,
        )
}