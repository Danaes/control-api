package com.bikeup.control.api.authentication.outbound.persistance.adapter

import com.bikeup.control.api.authentication.core.application.port.persistance.UserPort
import com.bikeup.control.api.authentication.core.application.usecase.LogInUserQry
import com.bikeup.control.api.authentication.core.application.usecase.SingUpUserCmd
import com.bikeup.control.api.authentication.core.domain.exception.UserNotFoundException
import com.bikeup.control.api.authentication.core.domain.model.User
import com.bikeup.control.api.authentication.outbound.persistance.entity.UserEntity
import com.bikeup.control.api.authentication.outbound.persistance.repository.UserEntityRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.BadRequestException

@ApplicationScoped
class UserAdapter(
    private val userEntityRepository: UserEntityRepository
) : UserPort {

    override fun save(singUpUserCmd: SingUpUserCmd): User {
        if (userEntityRepository.exists(singUpUserCmd.email)) throw BadRequestException("User already exists")

        val userEntity = UserEntity.create(singUpUserCmd)
        userEntity.persist()

        return userEntity.toModel()
    }

    override fun find(logInUserQry: LogInUserQry): User {
        val userEntity = userEntityRepository.findByEmailAndPassword(logInUserQry)
        return userEntity?.toModel() ?: throw UserNotFoundException("Invalid email or password")
    }

}