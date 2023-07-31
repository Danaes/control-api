package com.bikeup.control.api.authentication.outbound.persistance.entity

import com.bikeup.control.api.authentication.core.application.usecase.SingUpUserCmd
import com.bikeup.control.api.authentication.core.domain.model.User
import com.bikeup.control.api.authentication.core.domain.valueobject.Role
import io.quarkus.mongodb.panache.common.MongoEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoEntityBase
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

@MongoEntity(collection = "users")
data class UserEntity @BsonCreator constructor(
    @BsonId var id: String,
    @BsonProperty("username") val username: String,
    @BsonProperty("surname") val surname: String,
    @BsonProperty("email") val email: String,
    @BsonProperty("password") val password: String,
    @BsonProperty("roles") val roles: Set<Role>
) : PanacheMongoEntityBase() {

    fun toModel(): User =
        User(id = id, username = username, surname = surname, email = email, roles = roles)

    companion object {
        fun create(singUpUserCmd: SingUpUserCmd): UserEntity =
            UserEntity(
                id = ObjectId().toString(),
                username = singUpUserCmd.username,
                surname = singUpUserCmd.surname,
                email = singUpUserCmd.email,
                password = singUpUserCmd.password,
                roles = setOf(Role.USER)
            )
    }
}
