package com.bikeup.control.api.authentication.core.domain.exception

import java.lang.RuntimeException

class UserNotFoundException(message: String) : RuntimeException(message)