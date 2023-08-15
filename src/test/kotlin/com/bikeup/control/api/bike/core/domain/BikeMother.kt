package com.bikeup.control.api.bike.core.domain

import com.bikeup.control.api.bike.core.domain.model.Bike
import com.bikeup.control.api.bike.core.domain.model.Equipment
import org.bson.types.ObjectId

object BikeMother {

    fun of(
        id: String = ObjectId().toString(),
        userId: String = ObjectId().toString(),
        brand: String = "MMR",
        model: String = "Adrenaline 00 plus",
        year: Int = 2023,
        distance: Double = 0.0,
        equipments: List<Equipment> = emptyList()
    ): Bike = Bike(
        id = id,
        userId = userId,
        brand = brand,
        model = model,
        year = year,
        distance = distance,
        equipments = equipments,
    )
}