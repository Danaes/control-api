package com.bikeup.control.api.bike.core.application

import com.bikeup.control.api.bike.core.application.port.input.service.BikeServicePort
import com.bikeup.control.api.bike.core.application.port.output.persistence.BikeRepositoryPort
import com.bikeup.control.api.bike.core.application.port.output.persistence.EquipmentRepositoryPort
import com.bikeup.control.api.bike.core.application.usecase.BikeCreateCmd
import com.bikeup.control.api.bike.core.application.usecase.BikeResponse
import com.bikeup.control.api.bike.core.application.usecase.BikeUpdateCmd
import com.bikeup.control.api.bike.core.application.usecase.EquipmentUpdateCmd
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class BikeServiceAdapter(
    private val bikeRepositoryPort: BikeRepositoryPort,
    private val equipmentRepositoryPort: EquipmentRepositoryPort
) : BikeServicePort {
    override fun save(bikeCreateCmd: BikeCreateCmd): BikeResponse {
        val bikeCreated = bikeRepositoryPort.save(bikeCreateCmd)
        return BikeResponse.build(bikeCreated)
    }

    override fun update(bikeUpdateCmd: BikeUpdateCmd): BikeResponse {
        val bikeUpdated = bikeRepositoryPort.update(bikeUpdateCmd)
        return BikeResponse.build(bikeUpdated)
    }

    override fun find(userId: String): List<BikeResponse> {
        val bikes = bikeRepositoryPort.find(userId)
        return bikes.map { BikeResponse.build(it) }
    }

    override fun find(userId: String, bikeId: String): BikeResponse {
        val bike = bikeRepositoryPort.find(userId, bikeId)
        return BikeResponse.build(bike)
    }

    override fun delete(userId: String, bikeId: String) = bikeRepositoryPort.delete(userId, bikeId)

    override fun increaseDistance(userId: String, bikeId: String, distance: Double): BikeResponse {
        val bike = bikeRepositoryPort.find(userId, bikeId).increaseDistance(distance)

        bike.equipments.forEach { equipmentRepositoryPort.update(EquipmentUpdateCmd.build(it)) }

        return update(BikeUpdateCmd.build(bike).addUserId(userId))
    }

}