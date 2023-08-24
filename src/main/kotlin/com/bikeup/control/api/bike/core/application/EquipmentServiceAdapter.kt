package com.bikeup.control.api.bike.core.application

import com.bikeup.control.api.bike.core.application.port.input.service.EquipmentServicePort
import com.bikeup.control.api.bike.core.application.port.output.persistence.EquipmentRepositoryPort
import com.bikeup.control.api.bike.core.application.usecase.EquipmentCreateCmd
import com.bikeup.control.api.bike.core.application.usecase.EquipmentResponse
import com.bikeup.control.api.bike.core.application.usecase.EquipmentUpdateCmd
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class EquipmentServiceAdapter(
    private val equipmentRepositoryPort: EquipmentRepositoryPort
) : EquipmentServicePort {
    override fun save(equipmentCreateCmd: EquipmentCreateCmd): EquipmentResponse {
        val equipmentCreated = equipmentRepositoryPort.save(equipmentCreateCmd)
        return EquipmentResponse.map(equipmentCreated)
    }

    override fun update(equipmentUpdateCmd: EquipmentUpdateCmd): EquipmentResponse {
        val equipmentUpdated = equipmentRepositoryPort.update(equipmentUpdateCmd)
        return EquipmentResponse.map(equipmentUpdated)
    }

    override fun delete(bikeId: String, equipmentId: String) = equipmentRepositoryPort.delete(equipmentId, bikeId)
}