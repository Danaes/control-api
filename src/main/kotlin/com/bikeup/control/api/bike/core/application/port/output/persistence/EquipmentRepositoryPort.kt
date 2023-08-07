package com.bikeup.control.api.bike.core.application.port.output.persistence

import com.bikeup.control.api.bike.core.application.usecase.EquipmentCreateCmd
import com.bikeup.control.api.bike.core.application.usecase.EquipmentUpdateCmd
import com.bikeup.control.api.bike.core.domain.model.Equipment

interface EquipmentRepositoryPort {

    fun save(equipmentCreateCmd: EquipmentCreateCmd): Equipment
    fun findByBikeId(bikeId: String): List<Equipment>
    fun update(equipmentUpdateCmd: EquipmentUpdateCmd): Equipment
    fun delete(id: String, bikeId: String)
}