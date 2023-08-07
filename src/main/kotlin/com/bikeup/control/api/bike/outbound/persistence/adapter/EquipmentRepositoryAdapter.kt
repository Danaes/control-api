package com.bikeup.control.api.bike.outbound.persistence.adapter

import com.bikeup.control.api.bike.core.application.port.output.persistence.EquipmentRepositoryPort
import com.bikeup.control.api.bike.core.application.usecase.EquipmentCreateCmd
import com.bikeup.control.api.bike.core.application.usecase.EquipmentUpdateCmd
import com.bikeup.control.api.bike.core.domain.exception.EquipmentNotFoundException
import com.bikeup.control.api.bike.core.domain.model.Equipment
import com.bikeup.control.api.bike.outbound.persistence.entity.EquipmentEntity
import com.bikeup.control.api.bike.outbound.persistence.repository.EquipmentEntityRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class EquipmentRepositoryAdapter(
    private val bikeRepositoryAdapter: BikeRepositoryAdapter,
    private val equipmentEntityRepository: EquipmentEntityRepository
) : EquipmentRepositoryPort {
    override fun save(equipmentCreateCmd: EquipmentCreateCmd): Equipment {
        bikeRepositoryAdapter.checkIfExists(equipmentCreateCmd.bikeId)

        val equipmentEntity = EquipmentEntity.create(equipmentCreateCmd)
        equipmentEntityRepository.persist(equipmentEntity)

        return equipmentEntity.toDomain()
    }

    override fun findByBikeId(bikeId: String): List<Equipment> {
        val equipmentEntities = equipmentEntityRepository.findByBikeId(bikeId)
        return equipmentEntities.map { it.toDomain() }
    }

    override fun update(equipmentUpdateCmd: EquipmentUpdateCmd): Equipment {
        bikeRepositoryAdapter.checkIfExists(equipmentUpdateCmd.bikeId)

        val equipmentEntity = equipmentEntityRepository.findByPK(equipmentUpdateCmd.id, equipmentUpdateCmd.bikeId!!)
            ?: throw EquipmentNotFoundException("Equipment with id ${equipmentUpdateCmd.id} not exists")

        val equipmentEntityUpdated = equipmentEntity.update(equipmentUpdateCmd)
        equipmentEntityRepository.update(equipmentEntityUpdated)

        return equipmentEntityUpdated.toDomain()
    }


    override fun delete(id: String, bikeId: String) = equipmentEntityRepository.deleteByIdAndBikeId(id, bikeId)
}