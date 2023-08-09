package com.bikeup.control.api.authentication.core.application.usecase

import com.bikeup.control.api.authentication.core.application.UserLogInQryMother
import jakarta.ws.rs.BadRequestException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UserLogInQryTest {

    @Test
    fun init_whenParametersAreCorrect_shouldReturnUserSingUpCmd() {
        org.junit.jupiter.api.assertDoesNotThrow { UserLogInQryMother.of() }
    }

    @Test
    fun init_whenEmailIsBlank_shouldThrowBadRequestException() {
        val expected = "Email parameter may not be blank"

        val exception = org.junit.jupiter.api.assertThrows<BadRequestException> { UserLogInQryMother.of(email = "") }

        assertEquals(expected, exception.message!!)
    }

    @Test
    fun init_whenPasswordIsBlank_shouldThrowBadRequestException() {
        val expected = "Password parameter may not be blank"

        val exception =
            org.junit.jupiter.api.assertThrows<BadRequestException> { UserLogInQryMother.of(password = "") }

        assertEquals(expected, exception.message!!)
    }
}