package com.bikeup.control.api.authentication.core.application.port.output.persistance

import com.bikeup.control.api.authentication.core.application.usecase.UserLogInQry
import com.bikeup.control.api.authentication.core.application.usecase.UserSingUpCmd
import com.bikeup.control.api.authentication.core.domain.model.User

interface UserRepositoryPort {
    fun save(userSingUpCmd: UserSingUpCmd): User
    fun find(userLogInQry: UserLogInQry): User
}