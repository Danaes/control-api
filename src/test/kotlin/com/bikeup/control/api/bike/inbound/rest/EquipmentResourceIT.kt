package com.bikeup.control.api.bike.inbound.rest

import com.bikeup.control.api.bike.core.application.EquipmentCreateCmdMother
import com.bikeup.control.api.bike.core.application.EquipmentUpdateCmdMother
import com.bikeup.control.api.bike.core.application.port.output.persistence.EquipmentRepositoryPort
import com.bikeup.control.api.bike.core.application.usecase.EquipmentResponse
import com.bikeup.control.api.bike.core.domain.EquipmentMother
import com.bikeup.control.api.bike.core.domain.model.Equipment
import com.bikeup.control.api.common.core.domain.security.USER_ROLE
import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.security.TestSecurity
import io.restassured.RestAssured
import jakarta.ws.rs.core.Response
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@QuarkusTest
class EquipmentResourceIT {

    @InjectMock
    private lateinit var equipmentRepositoryPort: EquipmentRepositoryPort

    @Test
    @TestSecurity(user = "user", roles = [OTHER_ROLE])
    fun deleteEquipment_whenRoleIsNotAllowed_shouldReturnForbiddenStatusCode() {
        val basePath = "${buildBasePath()}/${EQUIPMENT.id}"

        val response = RestAssured.given().`when`().delete(basePath)

        response.then().statusCode(Response.Status.FORBIDDEN.statusCode)
    }

    @Test
    @TestSecurity(user = "user", roles = [USER_ROLE])
    fun createEquipment_whenEquipmentCreateCmd_shouldReturnCreatedStatusCode() {
        val basePath = buildBasePath()
        val equipmentCreateCmd = EquipmentCreateCmdMother.of(bikeId = BIKE_ID)
        `when`(equipmentRepositoryPort.save(equipmentCreateCmd)).thenReturn(EQUIPMENT)

        val response = RestAssured.given().`when`().header("Content-Type", "application/json").body(equipmentCreateCmd)
            .post(basePath)

        val equipment =
            response.then().statusCode(Response.Status.CREATED.statusCode).extract().`as`(EquipmentResponse::class.java)
        assertResponse(EQUIPMENT, equipment)
        verify(equipmentRepositoryPort).save(equipmentCreateCmd)
    }

    @Test
    @TestSecurity(user = "user", roles = [USER_ROLE])
    fun updateEquipment_whenEquipmentUpdateCmd_shouldReturnAcceptedStatusCode() {
        val basePath = buildBasePath()
        val model = "XTR"
        val equipmentUpdateCmd = EquipmentUpdateCmdMother.of(bikeId = BIKE_ID, model = model)
        val equipmentExpected = EQUIPMENT.copy(model = model)
        `when`(equipmentRepositoryPort.update(equipmentUpdateCmd)).thenReturn(equipmentExpected)

        val response = RestAssured.given().`when`().header("Content-Type", "application/json").body(equipmentUpdateCmd)
            .patch(basePath)

        val equipment = response.then().statusCode(Response.Status.ACCEPTED.statusCode).extract()
            .`as`(EquipmentResponse::class.java)
        assertResponse(equipmentExpected, equipment)
        verify(equipmentRepositoryPort).update(equipmentUpdateCmd)
    }

    @Test
    @TestSecurity(user = "user", roles = [USER_ROLE])
    fun deleteEquipment_whenMethodIsInvoked_shouldReturnNoContentStatusCode() {
        val basePath = "${buildBasePath()}/${EQUIPMENT.id}"

        val response = RestAssured.given().`when`().delete(basePath)

        response.then().statusCode(Response.Status.NO_CONTENT.statusCode)
        verify(equipmentRepositoryPort).delete(EQUIPMENT.id, BIKE_ID)
    }

    private fun assertResponse(expected: Equipment, result: EquipmentResponse) {
        assertEquals(expected.id, result.id)
        assertEquals(expected.brand, result.brand)
        assertEquals(expected.model, result.model)
        assertEquals(expected.distance, result.distance)
        assertEquals(expected.status, result.status)
        assertEquals(expected.type, result.type)
    }

    private fun buildBasePath() = "bike/${BIKE_ID}/equipment"

    private companion object {
        const val OTHER_ROLE = "OTHER_ROLE"
        val EQUIPMENT = EquipmentMother.of()
        val BIKE_ID = EQUIPMENT.bikeId
    }
}