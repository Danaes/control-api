package com.bikeup.control.api.authentication.core.application.port.service

import com.bikeup.control.api.authentication.core.application.usecase.AuthenticationResponse
import com.bikeup.control.api.authentication.core.application.usecase.LogInUserQry
import com.bikeup.control.api.authentication.core.application.usecase.SingUpUserCmd

interface AuthenticationPort {

    fun singUp(singUpUserCmd: SingUpUserCmd): AuthenticationResponse
    fun logIn(logInUserQry: LogInUserQry): AuthenticationResponse

}