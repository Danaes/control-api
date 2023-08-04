package com.bikeup.control.api.bike.core.application.port.output.persistence

import com.bikeup.control.api.bike.core.application.usecase.BikeCreateCmd
import com.bikeup.control.api.bike.core.application.usecase.BikeUpdateCmd
import com.bikeup.control.api.bike.core.domain.model.Bike

interface BikeRepositoryPort {

    fun save(bikeCreateCmd: BikeCreateCmd): Bike
    fun update(bikeUpdateCmd: BikeUpdateCmd): Bike
    fun find(userId: String): List<Bike>
    fun find(userId: String, bikeId: String): Bike
    fun delete(userId: String, bikeId: String)
}