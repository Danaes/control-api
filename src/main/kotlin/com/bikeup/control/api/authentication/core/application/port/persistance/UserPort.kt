package com.bikeup.control.api.authentication.core.application.port.persistance

import com.bikeup.control.api.authentication.core.application.usecase.LogInUserQry
import com.bikeup.control.api.authentication.core.application.usecase.SingUpUserCmd
import com.bikeup.control.api.authentication.core.domain.model.User

interface UserPort {
    fun save(singUpUserCmd: SingUpUserCmd): User
    fun find(logInUserQry: LogInUserQry): User
}