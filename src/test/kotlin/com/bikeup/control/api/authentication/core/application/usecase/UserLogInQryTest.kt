package com.bikeup.control.api.authentication.core.application.usecase

import com.bikeup.control.api.authentication.core.application.UserLogInQryMother
import jakarta.ws.rs.BadRequestException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class UserLogInQryTest {

    @Test
    internal fun init_whenParametersAreCorrect_shouldReturnUserSingUpCmd() {
        assertDoesNotThrow { UserLogInQryMother.of() }
    }

    @Test
    internal fun init_whenEmailIsBlank_shouldThrowBadRequestException() {
        val expected = "Email parameter may not be blank"

        val exception = assertThrows<BadRequestException> { UserLogInQryMother.of(email = "") }

        assertEquals(expected, exception.message!!)
    }

    @Test
    internal fun init_whenPasswordIsBlank_shouldThrowBadRequestException() {
        val expected = "Password parameter may not be blank"

        val exception = assertThrows<BadRequestException> { UserLogInQryMother.of(password = "") }

        assertEquals(expected, exception.message!!)
    }
}