package com.bikeup.control.api.authentication.outbound.persistence.repository

import com.bikeup.control.api.authentication.core.application.UserLogInQryMother
import com.bikeup.control.api.authentication.core.application.UserSingUpCmdMother
import com.bikeup.control.api.authentication.outbound.persistence.entity.UserEntity
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.bson.types.ObjectId
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserEntityRepositoryTest {

    @Inject
    private lateinit var sut: UserEntityRepository

    @BeforeAll
    fun setUp() {
        sut.persist(USER_ENTITY)
    }

    @Test
    internal fun findByEmailAndPassword_whenUserLogInQryExists_shouldReturnUserEntity() {
        val userLogInQry = UserLogInQryMother.of(email = USER_ENTITY.email, password = USER_ENTITY.password)

        val result = sut.findByEmailAndPassword(userLogInQry)

        assertEquals(USER_ENTITY, result)
    }

    @Test
    internal fun findByEmailAndPassword_whenUserLogInQryNotExists_shouldReturnNull() {
        val result = sut.findByEmailAndPassword(UserLogInQryMother.of())

        assertNull(result)
    }

    private fun createEmailParameters() = listOf(
        Arguments.of(USER_ENTITY.email, true),
        Arguments.of("fake@bikeup.es", false)
    )

    @ParameterizedTest
    @MethodSource("createEmailParameters")
    fun checkEmail_whenEmailIsGiven_shouldReturnIfExists(email: String, expected: Boolean) {
        val result = sut.checkEmail(email)

        assertEquals(expected, result)
    }

    @Test
    internal fun checkNotExists_whenIdExists_shouldReturnFalse() {
        val result = sut.checkNotExists(USER_ENTITY.id)

        assertFalse(result)
    }

    @Test
    internal fun checkNotExists_whenIdNotExists_shouldReturnTrue() {
        val result = sut.checkNotExists(ObjectId().toString())

        assertTrue(result)
    }

    @AfterAll
    fun tearDown() {
        sut.delete(USER_ENTITY)
    }

    private companion object {
        val USER_ENTITY = UserEntity.create(UserSingUpCmdMother.of())
    }
}