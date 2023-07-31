package com.bikeup.control.api.authentication.core.domain.model

import com.bikeup.control.api.authentication.core.domain.valueobject.Role

data class User(
    val id: String,
    val username: String,
    val surname: String,
    val email: String,
    val roles: Set<Role>
)
