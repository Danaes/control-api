package com.bikeup.control.api.authentication.outbound.persistance.repository

import com.bikeup.control.api.authentication.core.application.usecase.LogInUserQry
import com.bikeup.control.api.authentication.outbound.persistance.entity.UserEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserEntityRepository : PanacheMongoRepository<UserEntity> {

    fun findByEmailAndPassword(logInUserQry: LogInUserQry): UserEntity? =
        find("email = ?1 and password = ?2", logInUserQry.email, logInUserQry.password).firstResult()

    fun exists(email: String): Boolean = count("email", email) != 0L
}