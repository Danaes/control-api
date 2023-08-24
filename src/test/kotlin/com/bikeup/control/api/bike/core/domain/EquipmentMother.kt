package com.bikeup.control.api.bike.core.domain

import com.bikeup.control.api.bike.core.domain.model.Equipment
import com.bikeup.control.api.bike.core.domain.valueobject.EquipmentStatus
import com.bikeup.control.api.bike.core.domain.valueobject.EquipmentType
import org.bson.types.ObjectId

object EquipmentMother {

    fun of(
        id: String = ObjectId().toString(),
        bikeId: String = ObjectId().toString(),
        brand: String = "SRAM",
        model: String = "GX AXS",
        distance: Double = 0.0,
        status: EquipmentStatus = EquipmentStatus.ACTIVE,
        type: EquipmentType = EquipmentType.ELECTRONIC_GEAR_SHIFTING_SYSTEM
    ): Equipment =
        Equipment(
            id = id,
            bikeId = bikeId,
            brand = brand,
            model = model,
            distance = distance,
            status = status,
            type = type,
        )
}

