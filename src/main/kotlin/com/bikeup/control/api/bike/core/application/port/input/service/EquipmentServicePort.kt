package com.bikeup.control.api.bike.core.application.port.input.service

import com.bikeup.control.api.bike.core.application.usecase.EquipmentCreateCmd
import com.bikeup.control.api.bike.core.application.usecase.EquipmentResponse
import com.bikeup.control.api.bike.core.application.usecase.EquipmentUpdateCmd

interface EquipmentServicePort {

    fun save(equipmentCreateCmd: EquipmentCreateCmd): EquipmentResponse
    fun update(equipmentUpdateCmd: EquipmentUpdateCmd): EquipmentResponse
    fun delete(bikeId: String, equipmentId: String)
}
