package com.bikeup.control.api.bike.core.application.port.input.service

import com.bikeup.control.api.bike.core.application.usecase.BikeCreateCmd
import com.bikeup.control.api.bike.core.application.usecase.BikeResponse
import com.bikeup.control.api.bike.core.application.usecase.BikeUpdateCmd

interface BikeServicePort {
    fun save(bikeCreateCmd: BikeCreateCmd): BikeResponse
    fun update(bikeUpdateCmd: BikeUpdateCmd): BikeResponse
    fun find(userId: String): List<BikeResponse>
    fun find(userId: String, bikeId: String): BikeResponse
    fun delete(userId: String, bikeId: String)
    fun increaseDistance(userId: String, bikeId: String, distance: Double): BikeResponse
}