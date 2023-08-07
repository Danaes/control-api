package com.bikeup.control.api.bike.outbound.persistence.entity

import com.bikeup.control.api.bike.core.application.usecase.EquipmentCreateCmd
import com.bikeup.control.api.bike.core.application.usecase.EquipmentUpdateCmd
import com.bikeup.control.api.bike.core.domain.model.Equipment
import com.bikeup.control.api.bike.core.domain.valueobject.EquipmentStatus
import com.bikeup.control.api.bike.core.domain.valueobject.EquipmentType
import io.quarkus.mongodb.panache.common.MongoEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoEntityBase
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import java.time.Instant

@MongoEntity(collection = "equipments")
data class EquipmentEntity @BsonCreator constructor(
    @BsonId var id: String,
    @BsonProperty("bikeId") val bikeId: String,
    @BsonProperty("brand") val brand: String,
    @BsonProperty("model") val model: String,
    @BsonProperty("distance") val distance: Double,
    @BsonProperty("status") val status: EquipmentStatus,
    @BsonProperty("type") val type: EquipmentType,
    @BsonProperty("createdAt") val createdAt: String,
    @BsonProperty("updatedAt") val updatedAt: String?,
) : PanacheMongoEntityBase() {

    fun update(equipmentUpdateCmd: EquipmentUpdateCmd): EquipmentEntity {
        var equipmentEntity = this

        equipmentUpdateCmd.brand?.let { equipmentEntity = equipmentEntity.copy(brand = it) }
        equipmentUpdateCmd.model?.let { equipmentEntity = equipmentEntity.copy(model = it) }
        equipmentUpdateCmd.distance?.let { equipmentEntity = equipmentEntity.copy(distance = it) }
        equipmentUpdateCmd.type?.let { equipmentEntity = equipmentEntity.copy(type = it) }

        return equipmentEntity.copy(updatedAt = Instant.now().toString())
    }

    fun toDomain(): Equipment =
        Equipment(
            id = this.id,
            bikeId = this.bikeId,
            brand = this.brand,
            model = this.model,
            distance = this.distance,
            status = this.status,
            type = this.type,
        )

    companion object {
        fun create(equipmentCreateCmd: EquipmentCreateCmd): EquipmentEntity =
            EquipmentEntity(
                id = ObjectId().toString(),
                bikeId = equipmentCreateCmd.bikeId!!,
                brand = equipmentCreateCmd.brand,
                model = equipmentCreateCmd.model,
                distance = equipmentCreateCmd.distance,
                status = EquipmentStatus.ACTIVE,
                type = equipmentCreateCmd.type,
                createdAt = Instant.now().toString(),
                updatedAt = null
            )
    }
}
