package com.bikeup.control.api.authentication.inbound.rest

import com.bikeup.control.api.authentication.core.application.UserLogInQryMother
import com.bikeup.control.api.authentication.core.application.UserSingUpCmdMother
import com.bikeup.control.api.authentication.core.application.port.output.persistance.UserRepositoryPort
import com.bikeup.control.api.authentication.core.domain.model.User
import com.bikeup.control.api.common.core.domain.security.USER_ROLE
import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.response.Response
import jakarta.ws.rs.core.Response.Status
import org.bson.types.ObjectId
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@QuarkusTest
class AuthenticationResourceIT {

    @InjectMock
    private lateinit var userRepositoryPort: UserRepositoryPort

    @Test
    internal fun signUp_whenUserSignUpCmdIsCorrect_shouldReturnCreatedStatusCode() {
        `when`(userRepositoryPort.save(USER_SING_UP_CMD)).thenReturn(USER)

        val response = given()
            .`when`()
            .header("Content-Type", "application/json")
            .body(USER_SING_UP_CMD)
            .post(BASE_PATH)

        verify(userRepositoryPort).save(USER_SING_UP_CMD)
        assertResponse(response, Status.CREATED.statusCode)
    }

    @Test
    internal fun logIn_whenUserLogInQryIsCorrect_shouldReturnOkStatus() {
        `when`(userRepositoryPort.find(USER_LOG_IN_QRY)).thenReturn(USER)

        val response = given()
            .`when`()
            .header("Content-Type", "application/json")
            .body(USER_LOG_IN_QRY)
            .get(BASE_PATH)

        verify(userRepositoryPort).find(USER_LOG_IN_QRY)
        assertResponse(response, Status.OK.statusCode)
    }

    private fun assertResponse(response: Response, statusCode: Int) {
        response.then()
            .statusCode(statusCode)
            .body("id", equalTo(USER.id))
            .body("fullName", equalTo("${USER.username} ${USER.surname}"))
            .body("email", equalTo(USER.email))
            .body("token", notNullValue())
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