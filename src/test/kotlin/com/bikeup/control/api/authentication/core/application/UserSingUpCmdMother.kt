package com.bikeup.control.api.authentication.core.application

import com.bikeup.control.api.authentication.core.application.usecase.UserSingUpCmd
import java.util.*

object UserSingUpCmdMother {

    fun of(
        username: String = "username",
        surname: String = "surname",
        email: String = "email@bikeup.es",
        password: String = UUID.randomUUID().toString(),
    ): UserSingUpCmd =
        UserSingUpCmd(
            username = username,
            surname = surname,
            email = email,
            password = password,
        )
}