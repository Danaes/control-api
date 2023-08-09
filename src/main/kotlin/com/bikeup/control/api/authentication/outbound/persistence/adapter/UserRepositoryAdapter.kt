package com.bikeup.control.api.authentication.outbound.persistence.adapter

import com.bikeup.control.api.authentication.core.application.port.output.persistance.UserRepositoryPort
import com.bikeup.control.api.authentication.core.application.usecase.UserLogInQry
import com.bikeup.control.api.authentication.core.application.usecase.UserSingUpCmd
import com.bikeup.control.api.authentication.core.domain.exception.UserNotFoundException
import com.bikeup.control.api.authentication.core.domain.model.User
import com.bikeup.control.api.authentication.outbound.persistence.entity.UserEntity
import com.bikeup.control.api.authentication.outbound.persistence.repository.UserEntityRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.BadRequestException

@ApplicationScoped
class UserRepositoryAdapter(
    private val userEntityRepository: UserEntityRepository
) : UserRepositoryPort {

    override fun save(userSingUpCmd: UserSingUpCmd): User {
        if (userEntityRepository.checkEmail(userSingUpCmd.email)) throw BadRequestException("User already exists")

        val userEntity = UserEntity.create(userSingUpCmd)
        userEntityRepository.persist(userEntity)

        return userEntity.toDomain()
    }

    override fun find(userLogInQry: UserLogInQry): User {
        val userEntity = userEntityRepository.findByEmailAndPassword(userLogInQry)
        return userEntity?.toDomain() ?: throw UserNotFoundException("Invalid email or password")
    }

    fun checkExists(userId: String?) {
        check(userId != null) { "UserId must exist" }

        if (userEntityRepository.checkNotExists(userId))
            throw BadRequestException("User with id $userId not exists")
    }

}