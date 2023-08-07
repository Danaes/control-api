package com.bikeup.control.api.bike.outbound.persistence.repository

import com.bikeup.control.api.bike.outbound.persistence.entity.EquipmentEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class EquipmentEntityRepository : PanacheMongoRepository<EquipmentEntity> {

    fun findByBikeId(bikeId: String): List<EquipmentEntity> = find("bikeId = ?1", bikeId).list()

    fun findByPK(id: String, bikeId: String): EquipmentEntity? =
        find("_id = ?1 and bikeId = ?2", id, bikeId).firstResult()

    fun deleteByIdAndBikeId(id: String, bikeId: String) {
        delete("_id = ?1 and bikeId = ?2", id, bikeId)
    }
}