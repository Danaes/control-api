package com.bikeup.control.api.bike.outbound.persistence.entity

import com.bikeup.control.api.bike.core.application.usecase.BikeCreateCmd
import com.bikeup.control.api.bike.core.application.usecase.BikeUpdateCmd
import com.bikeup.control.api.bike.core.domain.model.Bike
import com.bikeup.control.api.bike.core.domain.model.Equipment
import io.quarkus.mongodb.panache.common.MongoEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoEntityBase
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import java.time.Instant

@MongoEntity(collection = "bikes")
data class BikeEntity @BsonCreator constructor(
    @BsonId var id: String,
    @BsonProperty("userId") val userId: String,
    @BsonProperty("brand") val brand: String,
    @BsonProperty("model") val model: String,
    @BsonProperty("year") val year: Int,
    @BsonProperty("distance") val distance: Double,
    @BsonProperty("createdAt") val createdAt: String,
    @BsonProperty("updatedAt") val updatedAt: String?,
) : PanacheMongoEntityBase() {

    fun update(bikeUpdateCmd: BikeUpdateCmd): BikeEntity {
        var bikeEntity = this

        bikeUpdateCmd.brand?.let { bikeEntity = bikeEntity.copy(brand = it) }
        bikeUpdateCmd.model?.let { bikeEntity = bikeEntity.copy(model = it) }
        bikeUpdateCmd.year?.let { bikeEntity = bikeEntity.copy(year = it) }
        bikeUpdateCmd.distance?.let { bikeEntity = bikeEntity.copy(distance = it) }

        return bikeEntity.copy(updatedAt = Instant.now().toString())
    }

    fun toDomain(equipments: List<Equipment> = emptyList()): Bike =
        Bike(
            id = this.id,
            userId = this.userId,
            brand = this.brand,
            model = this.model,
            year = this.year,
            distance = this.distance,
            equipments = equipments
        )

    companion object {
        fun create(bikeCreateCmd: BikeCreateCmd): BikeEntity =
            BikeEntity(
                id = ObjectId().toString(),
                userId = bikeCreateCmd.userId!!,
                brand = bikeCreateCmd.brand,
                model = bikeCreateCmd.model,
                year = bikeCreateCmd.year,
                distance = bikeCreateCmd.distance,
                createdAt = Instant.now().toString(),
                updatedAt = null
            )
    }
}
