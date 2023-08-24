package com.bikeup.control.api.authentication.core.application

import com.bikeup.control.api.authentication.core.application.usecase.UserLogInQry
import java.util.*

object UserLogInQryMother {

    fun of(
        email: String = "email@bikeup.es",
        password: String = UUID.randomUUID().toString(),
    ): UserLogInQry =
        UserLogInQry(
            email = email,
            password = password,
        )
}