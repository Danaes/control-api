package com.bikeup.control.api.bike.inbound.rest

import com.bikeup.control.api.bike.core.application.BikeCreateCmdMother
import com.bikeup.control.api.bike.core.application.BikeUpdateCmdMother
import com.bikeup.control.api.bike.core.application.port.output.persistence.BikeRepositoryPort
import com.bikeup.control.api.bike.core.application.usecase.BikeResponse
import com.bikeup.control.api.bike.core.application.usecase.BikeUpdateCmd
import com.bikeup.control.api.bike.core.domain.BikeMother
import com.bikeup.control.api.bike.core.domain.model.Bike
import com.bikeup.control.api.common.core.domain.security.USER_ROLE
import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.security.TestSecurity
import io.restassured.RestAssured
import jakarta.ws.rs.core.Response.Status
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@QuarkusTest
class BikeResourceIT {

    @InjectMock
    private lateinit var bikeRepositoryPort: BikeRepositoryPort

    @Test
    @TestSecurity(user = "user", roles = [OTHER_ROLE])
    fun getBikes_whenRoleIsNotAllowed_shouldReturnForbiddenStatusCode() {
        val basePath = buildBasePath()

        val response = RestAssured.`when`().get(basePath)

        response.then().statusCode(Status.FORBIDDEN.statusCode)
    }

    @Test
    @TestSecurity(user = "user", roles = [USER_ROLE])
    fun createBike_whenBikeCreateCmd_shouldReturnCreatedStatusCode() {
        val basePath = buildBasePath()
        val bikeCreateCmd = BikeCreateCmdMother.of(userId = USER_ID)
        `when`(bikeRepositoryPort.save(bikeCreateCmd)).thenReturn(BIKE)

        val response =
            RestAssured.given().`when`().header("Content-Type", "application/json").body(bikeCreateCmd).post(basePath)

        val bike = response.then().statusCode(Status.CREATED.statusCode).extract().`as`(BikeResponse::class.java)
        assertResponse(BIKE, bike)
        verify(bikeRepositoryPort).save(bikeCreateCmd)
    }

    @Test
    @TestSecurity(user = "user", roles = [USER_ROLE])
    fun getBikes_whenUserId_shouldReturnBikesWithThisUserId() {
        val basePath = buildBasePath()
        `when`(bikeRepositoryPort.find(USER_ID)).thenReturn(listOf(BIKE))

        val response = RestAssured.given().`when`().get(basePath)

        val bikes = response.then().statusCode(Status.OK.statusCode).extract().body().jsonPath()
            .getList(".", BikeResponse::class.java)
        assertTrue(bikes.isNotEmpty())
        assertResponse(BIKE, bikes.first())
        verify(bikeRepositoryPort).find(USER_ID)
    }

    @Test
    @TestSecurity(user = "user", roles = [USER_ROLE])
    fun getBike_whenBikeId_shouldReturnBike() {
        val basePath = "${buildBasePath()}/${BIKE.id}"
        `when`(bikeRepositoryPort.find(USER_ID, BIKE.id)).thenReturn(BIKE)

        val response = RestAssured.given().`when`().get(basePath)

        val bike = response.then().statusCode(Status.OK.statusCode).extract().`as`(BikeResponse::class.java)
        assertResponse(BIKE, bike)
        verify(bikeRepositoryPort).find(USER_ID, BIKE.id)
    }

    @Test
    @TestSecurity(user = "user", roles = [USER_ROLE])
    fun updateBike_whenBikeUpdateCmd_shouldReturnAcceptedStatusCode() {
        val basePath = buildBasePath()
        val expectedBrand = "TreK"
        val bikeUpdateCmd = BikeUpdateCmdMother.of(userId = USER_ID, brand = expectedBrand)
        val expected = BIKE.copy(id = bikeUpdateCmd.id, brand = expectedBrand)
        `when`(bikeRepositoryPort.update(bikeUpdateCmd)).thenReturn(expected)

        val response =
            RestAssured.given().`when`().header("Content-Type", "application/json").body(bikeUpdateCmd).patch(basePath)

        val bike = response.then().statusCode(Status.ACCEPTED.statusCode).extract().`as`(BikeResponse::class.java)
        assertResponse(expected, bike)
        verify(bikeRepositoryPort).update(bikeUpdateCmd)
    }

    @Test
    @TestSecurity(user = "user", roles = [USER_ROLE])
    fun increaseDistance_whenIncreaseDistance_shouldReturnAcceptedStatusCode() {
        val newDistance = 150.0
        val basePath = "${buildBasePath()}/${BIKE.id}"
        val expected = BIKE.increaseDistance(distance = newDistance)
        `when`(bikeRepositoryPort.find(USER_ID, BIKE.id)).thenReturn(BIKE)
        `when`(bikeRepositoryPort.update(BikeUpdateCmd.map(expected))).thenReturn(expected)

        val response = RestAssured.given().`when`().queryParam("distance", newDistance).patch(basePath)

        val bike = response.then().statusCode(Status.ACCEPTED.statusCode).extract().`as`(BikeResponse::class.java)
        assertResponse(expected, bike)
        verify(bikeRepositoryPort).find(USER_ID, BIKE.id)
        verify(bikeRepositoryPort).update(BikeUpdateCmd.map(expected))
    }

    @Test
    @TestSecurity(user = "user", roles = [USER_ROLE])
    fun deleteBike_whenMethodIsInvoked_shouldReturnNoContentStatusCode() {
        val basePath = "${buildBasePath()}/${BIKE.id}"

        val response = RestAssured.given().`when`().delete(basePath)

        response.then().statusCode(Status.NO_CONTENT.statusCode)
        verify(bikeRepositoryPort).delete(USER_ID, BIKE.id)
    }

    private fun assertResponse(expected: Bike, result: BikeResponse) {
        assertEquals(expected.id, result.id)
        assertEquals(expected.brand, result.brand)
        assertEquals(expected.model, result.model)
        assertEquals(expected.year, result.year)
        assertEquals(expected.distance, result.distance)
        assertTrue(result.equipments.isEmpty())
    }

    private fun buildBasePath() = "user/$USER_ID/bike"

    private companion object {
        const val OTHER_ROLE = "OTHER_ROLE"
        val USER_ID = ObjectId().toString()
        val BIKE = BikeMother.of(userId = USER_ID)
    }
}