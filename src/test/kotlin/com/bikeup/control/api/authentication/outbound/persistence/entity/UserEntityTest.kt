package com.bikeup.control.api.authentication.outbound.persistence.entity

import com.bikeup.control.api.authentication.core.application.UserSingUpCmdMother
import com.bikeup.control.api.common.core.domain.security.USER_ROLE
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class UserEntityTest {

    @Test
    internal fun create_whenUserSingUpCmd_shouldReturnUserEntity() {
        val userSingUpCmd = UserSingUpCmdMother.of()

        val result = UserEntity.create(userSingUpCmd)

        assertNotNull(result.id)
        assertEquals(userSingUpCmd.username, result.username)
        assertEquals(userSingUpCmd.surname, result.surname)
        assertEquals(userSingUpCmd.email, result.email)
        assertEquals(userSingUpCmd.password, result.password)
        assertEquals(USER_ROLE, result.roles.first())
        assertNotNull(result.createdAt)
        assertNull(result.updatedAt)
    }

    @Test
    internal fun toDomain_whenMethodIsInvoked_shouldReturnUser() {
        val userEntity = UserEntity.create(UserSingUpCmdMother.of())

        val result = userEntity.toDomain()

        assertNotNull(result.id)
        assertEquals(userEntity.username, result.username)
        assertEquals(userEntity.surname, result.surname)
        assertEquals(userEntity.email, result.email)
        assertEquals(USER_ROLE, result.roles.first())
    }
}