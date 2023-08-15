package com.bikeup.control.api.authentication.core.application.usecase

import com.bikeup.control.api.authentication.core.application.UserSingUpCmdMother
import jakarta.ws.rs.BadRequestException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class UserSingUpCmdTest {

    @Test
    internal fun init_whenParametersAreCorrect_shouldReturnUserSingUpCmd() {
        assertDoesNotThrow { UserSingUpCmdMother.of() }
    }

    @Test
    internal fun init_whenUsernameIsBlank_shouldThrowBadRequestException() {
        val expected = "Username parameter may not be blank"

        val exception = assertThrows<BadRequestException> { UserSingUpCmdMother.of(username = "") }

        assertEquals(expected, exception.message!!)
    }

    @Test
    internal fun init_whenSurnameIsBlank_shouldThrowBadRequestException() {
        val expected = "Surname parameter may not be blank"

        val exception = assertThrows<BadRequestException> { UserSingUpCmdMother.of(surname = "") }

        assertEquals(expected, exception.message!!)
    }

    @Test
    internal fun init_whenEmailIsBlank_shouldThrowBadRequestException() {
        val expected = "Email parameter may not be blank"

        val exception = assertThrows<BadRequestException> { UserSingUpCmdMother.of(email = "") }

        assertEquals(expected, exception.message!!)
    }

    @Test
    internal fun init_whenPasswordIsBlank_shouldThrowBadRequestException() {
        val expected = "Password parameter may not be blank"

        val exception = assertThrows<BadRequestException> { UserSingUpCmdMother.of(password = "") }

        assertEquals(expected, exception.message!!)
    }
}