package com.bikeup.control.api.bike.core.application

import com.bikeup.control.api.bike.core.application.usecase.EquipmentUpdateCmd
import com.bikeup.control.api.bike.core.domain.valueobject.EquipmentType
import org.bson.types.ObjectId

object EquipmentUpdateCmdMother {

    fun of(
        id: String = ObjectId().toString(),
        bikeId: String? = null,
        brand: String = "SRAM",
        model: String = "GX AXS",
        distance: Double = 0.0,
        type: EquipmentType = EquipmentType.ELECTRONIC_GEAR_SHIFTING_SYSTEM
    ): EquipmentUpdateCmd =
        EquipmentUpdateCmd(
            id = id,
            bikeId = bikeId,
            brand = brand,
            model = model,
            distance = distance,
            type = type,
        )
}