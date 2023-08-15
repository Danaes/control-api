package com.bikeup.control.api.bike.core.application

import com.bikeup.control.api.bike.core.application.usecase.BikeCreateCmd

object BikeCreateCmdMother {

    fun of(
        userId: String? = null,
        brand: String = "MMR",
        model: String = "Adrenaline 00 plus",
        year: Int = 2023,
        distance: Double = 0.0
    ): BikeCreateCmd =
        BikeCreateCmd(
            userId = userId,
            brand = brand,
            model = model,
            year = year,
            distance = distance,
        )
}
