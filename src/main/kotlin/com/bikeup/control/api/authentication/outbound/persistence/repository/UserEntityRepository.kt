package com.bikeup.control.api.authentication.outbound.persistence.repository

import com.bikeup.control.api.authentication.core.application.usecase.UserLogInQry
import com.bikeup.control.api.authentication.outbound.persistence.entity.UserEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserEntityRepository : PanacheMongoRepository<UserEntity> {

    fun findByEmailAndPassword(userLogInQry: UserLogInQry): UserEntity? =
        find("email = ?1 and password = ?2", userLogInQry.email, userLogInQry.password).firstResult()

    fun checkEmail(email: String): Boolean = count("email", email) == 1L

    fun checkIfExists(id: String): Boolean = count("_id", id) != 1L
}