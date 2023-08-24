package com.bikeup.control.api.bike.outbound.persistence.repository

import com.bikeup.control.api.bike.outbound.persistence.entity.BikeEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class BikeEntityRepository : PanacheMongoRepository<BikeEntity> {

    fun find(userId: String): List<BikeEntity> = find("userId", userId).list()
    fun findByPK(id: String, userId: String): BikeEntity? =
        find("_id = ?1 and userId = ?2", id, userId).firstResult()

    fun checkNotExists(id: String): Boolean = find("_id", id).list().isEmpty()
    fun deleteByIdAndUserId(id: String, userId: String) {
        delete("_id = ?1 and userId = ?2", id, userId)
    }
}