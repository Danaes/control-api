package com.bikeup.control.api.authentication.inbound.rest

import com.bikeup.control.api.authentication.core.application.UserLogInQryMother
import com.bikeup.control.api.authentication.core.application.UserSingUpCmdMother
import com.bikeup.control.api.authentication.core.application.port.output.persistance.UserRepositoryPort
import com.bikeup.control.api.authentication.core.domain.model.User
import com.bikeup.control.api.common.core.domain.security.USER_ROLE
import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import jakarta.ws.rs.core.Response
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@QuarkusTest
class AuthenticationResourceIT {

    @InjectMock
    private lateinit var userRepositoryPort: UserRepositoryPort

    @Test
    fun signUp_whenUserSignUpCmdIsCorrect_shouldReturnCreatedStatusCode() {
        `when`(userRepositoryPort.save(USER_SING_UP_CMD)).thenReturn(USER)

        given()
            .`when`()
            .header("Content-Type", "application/json")
            .body(USER_SING_UP_CMD)
            .post(BASE_PATH)
            .then()
            .statusCode(Response.Status.CREATED.statusCode)

        verify(userRepositoryPort).save(USER_SING_UP_CMD)
    }

    @Test
    fun logIn_whenUserLogInQryIsCorrect_shouldReturnOkStatus() {
        `when`(userRepositoryPort.find(USER_LOG_IN_QRY)).thenReturn(USER)

        given()
            .`when`()
            .header("Content-Type", "application/json")
            .body(USER_LOG_IN_QRY)
            .get(BASE_PATH)
            .then()
            .statusCode(Response.Status.OK.statusCode)

        verify(userRepositoryPort).find(USER_LOG_IN_QRY)
    }

    private companion object {
        const val BASE_PATH = "/auth"
        val USER_SING_UP_CMD = UserSingUpCmdMother.of()
        val USER_LOG_IN_QRY = UserLogInQryMother.of()
        val USER = User(
            id = ObjectId().toString(),
            username = USER_SING_UP_CMD.username,
            surname = USER_SING_UP_CMD.surname,
            email = USER_SING_UP_CMD.email,
            roles = setOf(USER_ROLE),
        )
    }
}