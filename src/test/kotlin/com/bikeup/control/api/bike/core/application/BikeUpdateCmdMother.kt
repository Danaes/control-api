package com.bikeup.control.api.bike.core.application

import com.bikeup.control.api.bike.core.application.usecase.BikeUpdateCmd
import org.bson.types.ObjectId

object BikeUpdateCmdMother {

    fun of(
        id: String = ObjectId().toString(),
        userId: String? = null,
        brand: String? = null,
        model: String? = null,
        year: Int? = null,
        distance: Double? = null
    ): BikeUpdateCmd =
        BikeUpdateCmd(
            id = id,
            userId = userId,
            brand = brand,
            model = model,
            year = year,
            distance = distance,
        )
}