package com.bikeup.control.api.bike.outbound.persistence.adapter

import com.bikeup.control.api.authentication.outbound.persistence.repository.UserEntityRepository
import com.bikeup.control.api.bike.core.application.port.output.persistence.BikeRepositoryPort
import com.bikeup.control.api.bike.core.application.usecase.BikeCreateCmd
import com.bikeup.control.api.bike.core.application.usecase.BikeUpdateCmd
import com.bikeup.control.api.bike.core.domain.exception.BikeNotFoundException
import com.bikeup.control.api.bike.core.domain.model.Bike
import com.bikeup.control.api.bike.outbound.persistence.entity.BikeEntity
import com.bikeup.control.api.bike.outbound.persistence.repository.BikeEntityRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.BadRequestException

@ApplicationScoped
class BikeRepositoryAdapter(
    private val bikeEntityRepository: BikeEntityRepository,
    private val userEntityRepository: UserEntityRepository
) : BikeRepositoryPort {
    override fun save(bikeCreateCmd: BikeCreateCmd): Bike {
        validateUserId(bikeCreateCmd.userId)

        val bikeEntity = BikeEntity.create(bikeCreateCmd)
        bikeEntityRepository.persist(bikeEntity)

        return bikeEntity.toDomain()
    }

    override fun update(bikeUpdateCmd: BikeUpdateCmd): Bike {
        validateUserId(bikeUpdateCmd.userId)

        val bikeEntity = findBikeEntity(bikeUpdateCmd.id, bikeUpdateCmd.userId!!)
        val bikeEntityUpdate = bikeEntity.update(bikeUpdateCmd)
        bikeEntityRepository.update(bikeEntityUpdate)

        return bikeEntityUpdate.toDomain()
    }

    override fun find(userId: String): List<Bike> {
        val bikeEntities = bikeEntityRepository.find(userId)
        return bikeEntities.map { it.toDomain() }
    }

    override fun find(userId: String, bikeId: String): Bike = findBikeEntity(bikeId, userId).toDomain()

    override fun delete(userId: String, bikeId: String) = bikeEntityRepository.deleteByIdAndUserId(bikeId, userId)

    private fun validateUserId(userId: String?) {
        check(userId != null) { "UserId must exist" }

        if (userEntityRepository.findById(userId) == null)
            throw BadRequestException("User with id $userId not exists")
    }

    private fun findBikeEntity(id: String, userId: String): BikeEntity =
        bikeEntityRepository.findByPK(id, userId) ?: throw BikeNotFoundException("Bike with id $id not exists")
}