package com.bikeup.control.api.authentication.core.application

import com.bikeup.control.api.authentication.core.application.port.output.persistance.UserRepositoryPort
import com.bikeup.control.api.authentication.core.application.usecase.AuthenticationResponse
import com.bikeup.control.api.authentication.core.domain.model.User
import com.bikeup.control.api.common.core.domain.security.USER_ROLE
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class AuthenticationServiceAdapterTest {

    private val userRepositoryPort = mock<UserRepositoryPort>()
    private lateinit var sut: AuthenticationServiceAdapter

    @BeforeEach
    internal fun init() {
        reset(userRepositoryPort)
        sut = AuthenticationServiceAdapter(ISSUER, userRepositoryPort)
    }

    @Test
    internal fun signUp_whenUserSingUpCmd_ShouldSaveAndReturnAuthenticationResponse() {
        val user = buildUser()
        val expected = AuthenticationResponse.map(user, ISSUER)
        `when`(userRepositoryPort.save(USER_SING_UP_CMD)).thenReturn(user)

        val result = sut.singUp(USER_SING_UP_CMD)

        verify(userRepositoryPort).save(USER_SING_UP_CMD)
        assertResult(expected, result)
    }

    @Test
    internal fun logIn_whenUserLogInQry_ShouldFindAndReturnAuthenticationResponse() {
        val user = buildUser()
        val expected = AuthenticationResponse.map(user, ISSUER)
        `when`(userRepositoryPort.find(USER_LOG_IN_QRY)).thenReturn(user)

        val result = sut.logIn(USER_LOG_IN_QRY)

        verify(userRepositoryPort).find(USER_LOG_IN_QRY)
        assertResult(expected, result)
    }

    private fun buildUser(): User =
        User(
            id = ObjectId().toString(),
            username = USER_SING_UP_CMD.username,
            surname = USER_SING_UP_CMD.surname,
            email = USER_SING_UP_CMD.email,
            roles = setOf(USER_ROLE)
        )

    private fun assertResult(expected: AuthenticationResponse, result: AuthenticationResponse) {
        assertEquals(expected.id, result.id)
        assertEquals(expected.fullName, result.fullName)
        assertEquals(expected.email, result.email)
    }

    private companion object {
        const val ISSUER = "https://bikeup.com/issuer"
        val USER_SING_UP_CMD = UserSingUpCmdMother.of()
        val USER_LOG_IN_QRY = UserLogInQryMother.of(
            email = USER_SING_UP_CMD.email,
            password = USER_SING_UP_CMD.password
        )
    }
}