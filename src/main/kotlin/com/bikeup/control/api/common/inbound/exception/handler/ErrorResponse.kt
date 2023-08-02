package com.bikeup.control.api.common.inbound.exception.handler

import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.Response.Status

data class ErrorResponse(
    val message: String,
    private val status: Status
) {
    fun toResponse(): Response = Response.ok(this).status(status).build()

}
