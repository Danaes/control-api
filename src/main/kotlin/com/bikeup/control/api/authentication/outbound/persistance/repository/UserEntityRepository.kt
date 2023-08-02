package com.bikeup.control.api.authentication.outbound.persistance.repository

import com.bikeup.control.api.authentication.core.application.usecase.UserLogInQry
import com.bikeup.control.api.authentication.outbound.persistance.entity.UserEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserEntityRepository : PanacheMongoRepository<UserEntity> {

    fun findByEmailAndPassword(userLogInQry: UserLogInQry): UserEntity? =
        find("email = ?1 and password = ?2", userLogInQry.email, userLogInQry.password).firstResult()

    fun exists(email: String): Boolean = count("email", email) != 0L
}