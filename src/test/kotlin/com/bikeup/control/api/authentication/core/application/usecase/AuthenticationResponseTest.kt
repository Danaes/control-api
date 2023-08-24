package com.bikeup.control.api.authentication.core.application.usecase

import com.bikeup.control.api.authentication.core.domain.UserMother
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class AuthenticationResponseTest {

    @Test
    internal fun map_whenUserAndIssuerAreGiven_shouldReturnAuthenticationResponse() {
        val user = UserMother.of()
        val issuer = "https://bikeup.com/issuer"

        val result = AuthenticationResponse.map(user, issuer)

        assertEquals(user.id, result.id)
        assertEquals("${user.username} ${user.surname}", result.fullName)
        assertEquals(user.email, result.email)
        assertNotNull(result.token)
    }
}