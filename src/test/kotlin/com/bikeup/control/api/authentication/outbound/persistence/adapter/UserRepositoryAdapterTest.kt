package com.bikeup.control.api.authentication.outbound.persistence.adapter

import com.bikeup.control.api.authentication.core.application.UserLogInQryMother
import com.bikeup.control.api.authentication.core.application.UserSingUpCmdMother
import com.bikeup.control.api.authentication.core.domain.exception.UserNotFoundException
import com.bikeup.control.api.authentication.outbound.persistence.entity.UserEntity
import com.bikeup.control.api.authentication.outbound.persistence.repository.UserEntityRepository
import com.bikeup.control.api.common.ClockTestUtils
import jakarta.ws.rs.BadRequestException
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mockito.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryAdapterTest {

    private val userEntityRepository = mock<UserEntityRepository>()
    private lateinit var sut: UserRepositoryAdapter

    @BeforeAll
    internal fun setUp() {
        ClockTestUtils.mockClock()
    }

    @BeforeEach
    internal fun init() {
        reset(userEntityRepository)
        sut = UserRepositoryAdapter(userEntityRepository)
    }

    @Test
    internal fun save_whenEmailExists_shouldThrowBadRequestException() {
        `when`(userEntityRepository.checkEmail(USER_SING_UP_CMD.email)).thenReturn(true)

        val exception = assertThrows<BadRequestException> { sut.save(USER_SING_UP_CMD) }

        assertEquals("User already exists", exception.message!!)
    }

    @Test
    internal fun save_whenEmailNotExists_shouldSaveUserAndReturnIt() {
        val expected = UserEntity.create(USER_SING_UP_CMD)

        val result = sut.save(USER_SING_UP_CMD)

        assertEquals(expected.copy(id = result.id).toDomain(), result)
        verify(userEntityRepository).persist(expected.copy(id = result.id))
    }

    @Test
    internal fun find_whenUserLogInQryNotExists_shouldThrowUserNotFoundException() {
        `when`(userEntityRepository.findByEmailAndPassword(USER_LOG_IN_QRY)).thenReturn(null)

        val exception = assertThrows<UserNotFoundException> { sut.find(USER_LOG_IN_QRY) }

        assertEquals("Invalid email or password", exception.message!!)
    }

    @Test
    internal fun find_whenUserLogInQryExists_shouldReturnUser() {
        val userEntity = UserEntity.create(USER_SING_UP_CMD)
        val expected = userEntity.toDomain()
        `when`(userEntityRepository.findByEmailAndPassword(USER_LOG_IN_QRY)).thenReturn(userEntity)

        val result = sut.find(USER_LOG_IN_QRY)

        verify(userEntityRepository).findByEmailAndPassword(USER_LOG_IN_QRY)
        assertEquals(expected, result)
    }

    @Test
    internal fun checkExists_whenUserIdIsNull_shouldThrowIllegalStateException() {
        val exception = assertThrows<IllegalStateException> { sut.checkExists(null) }

        assertEquals("UserId must exist", exception.message!!)
    }

    @Test
    internal fun checkExists_whenUserIdNotExists_shouldThrowBadRequestException() {
        val userId = "userId"
        `when`(userEntityRepository.checkNotExists(userId)).thenReturn(true)

        val exception = assertThrows<BadRequestException> { sut.checkExists(userId) }

        assertEquals("User with id $userId not exists", exception.message!!)
    }

    @Test
    internal fun checkExists_whenUserIdExists_shouldDoNothing() {
        val userId = "userId"
        `when`(userEntityRepository.checkNotExists(userId)).thenReturn(false)

        assertDoesNotThrow { sut.checkExists(userId) }
    }

    private companion object {
        val USER_SING_UP_CMD = UserSingUpCmdMother.of()
        val USER_LOG_IN_QRY = UserLogInQryMother.of()
    }
}