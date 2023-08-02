package com.bikeup.control.api.authentication.core.application.port.input.service

import com.bikeup.control.api.authentication.core.application.usecase.AuthenticationResponse
import com.bikeup.control.api.authentication.core.application.usecase.UserLogInQry
import com.bikeup.control.api.authentication.core.application.usecase.UserSingUpCmd

interface AuthenticationServicePort {

    fun singUp(userSingUpCmd: UserSingUpCmd): AuthenticationResponse
    fun logIn(userLogInQry: UserLogInQry): AuthenticationResponse

}