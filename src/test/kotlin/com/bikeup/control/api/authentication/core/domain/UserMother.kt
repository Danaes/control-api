package com.bikeup.control.api.authentication.core.domain

import com.bikeup.control.api.authentication.core.domain.model.User
import com.bikeup.control.api.common.core.domain.security.USER_ROLE
import org.bson.types.ObjectId

object UserMother {

    fun of(
        id: String = ObjectId().toString(),
        username: String = "username",
        surname: String = "surname",
        email: String = "email",
        roles: Set<String> = setOf(USER_ROLE)
    ): User =
        User(
            id = id,
            username = username,
            surname = surname,
            email = email,
            roles = roles,
        )
}