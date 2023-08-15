package com.bikeup.control.api.bike.outbound.persistence.adapter

import com.bikeup.control.api.authentication.outbound.persistence.adapter.UserRepositoryAdapter
import com.bikeup.control.api.bike.core.application.port.output.persistence.BikeRepositoryPort
import com.bikeup.control.api.bike.core.application.usecase.BikeCreateCmd
import com.bikeup.control.api.bike.core.application.usecase.BikeUpdateCmd
import com.bikeup.control.api.bike.core.domain.exception.BikeNotFoundException
import com.bikeup.control.api.bike.core.domain.model.Bike
import com.bikeup.control.api.bike.outbound.persistence.entity.BikeEntity
import com.bikeup.control.api.bike.outbound.persistence.repository.BikeEntityRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class BikeRepositoryAdapter(
    private val bikeEntityRepository: BikeEntityRepository,
    private val userRepositoryAdapter: UserRepositoryAdapter,
    private val equipmentRepositoryAdapter: EquipmentRepositoryAdapter
) : BikeRepositoryPort {
    override fun save(bikeCreateCmd: BikeCreateCmd): Bike {
        userRepositoryAdapter.checkExists(bikeCreateCmd.userId)

        val bikeEntity = BikeEntity.create(bikeCreateCmd)
        bikeEntityRepository.persist(bikeEntity)

        return bikeEntity.toDomain()
    }

    override fun update(bikeUpdateCmd: BikeUpdateCmd): Bike {
        userRepositoryAdapter.checkExists(bikeUpdateCmd.userId)

        val bikeEntity = findBikeEntity(bikeUpdateCmd.id, bikeUpdateCmd.userId!!)
        val bikeEntityUpdated = bikeEntity.update(bikeUpdateCmd)
        bikeEntityRepository.update(bikeEntityUpdated)

        return addEquipments(bikeEntityUpdated)
    }

    override fun find(userId: String): List<Bike> {
        val bikeEntities = bikeEntityRepository.find(userId)
        return bikeEntities.map { addEquipments(it) }
    }

    override fun find(userId: String, bikeId: String): Bike {
        val bikeEntity = findBikeEntity(bikeId, userId)
        return addEquipments(bikeEntity)
    }

    override fun delete(userId: String, bikeId: String) = bikeEntityRepository.deleteByIdAndUserId(bikeId, userId)

    fun checkExists(bikeId: String?) {
        check(bikeId != null) { "BikeId must exist" }

        if (bikeEntityRepository.checkNotExists(bikeId))
            throw BikeNotFoundException("Bike with id $bikeId not exists")
    }

    private fun addEquipments(bikeEntity: BikeEntity): Bike {
        val equipments = equipmentRepositoryAdapter.findByBikeId(bikeEntity.id)
        return bikeEntity.toDomain(equipments)
    }

    private fun findBikeEntity(id: String, userId: String): BikeEntity =
        bikeEntityRepository.findByPK(id, userId) ?: throw BikeNotFoundException("Bike with id $id not exists")
}