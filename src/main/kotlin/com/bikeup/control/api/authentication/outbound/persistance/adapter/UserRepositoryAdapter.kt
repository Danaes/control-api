package com.bikeup.control.api.authentication.outbound.persistance.adapter

import com.bikeup.control.api.authentication.core.application.port.output.persistance.UserRepositoryPort
import com.bikeup.control.api.authentication.core.application.usecase.UserLogInQry
import com.bikeup.control.api.authentication.core.application.usecase.UserSingUpCmd
import com.bikeup.control.api.authentication.core.domain.exception.UserNotFoundException
import com.bikeup.control.api.authentication.core.domain.model.User
import com.bikeup.control.api.authentication.outbound.persistance.entity.UserEntity
import com.bikeup.control.api.authentication.outbound.persistance.repository.UserEntityRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.BadRequestException

@ApplicationScoped
class UserRepositoryAdapter(
    private val userEntityRepository: UserEntityRepository
) : UserRepositoryPort {

    override fun save(userSingUpCmd: UserSingUpCmd): User {
        if (userEntityRepository.exists(userSingUpCmd.email)) throw BadRequestException("User already exists")

        val userEntity = UserEntity.create(userSingUpCmd)
        userEntity.persist()

        return userEntity.toModel()
    }

    override fun find(userLogInQry: UserLogInQry): User {
        val userEntity = userEntityRepository.findByEmailAndPassword(userLogInQry)
        return userEntity?.toModel() ?: throw UserNotFoundException("Invalid email or password")
    }

}