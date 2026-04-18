package com.propi.propiapi.services

import com.propi.propiapi.entities.UserEntity
import com.propi.propiapi.repositories.UserRepository
import com.propi.shared.dto.RegisterDTO
import com.propi.shared.dto.request.UserRequestDTO
import com.propi.shared.dto.response.UserResponseDTO
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

interface UserService {
    fun findAll(): List<UserResponseDTO>
    fun findById(id: Long): UserResponseDTO
    fun findByEmail(email: String): UserResponseDTO
    fun authenticate(email: String, password: String): UserResponseDTO
    fun register(request: RegisterDTO): UserResponseDTO
    fun update(id: Long, request: UserRequestDTO): UserResponseDTO
    fun delete(id: Long)
}

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    override fun findAll(): List<UserResponseDTO> =
        userRepository.findAll()
            .map { it.toResponse() }

    override fun findById(id: Long): UserResponseDTO =
        userRepository.findById(id)
            .orElseThrow { NoSuchElementException("User not found with id: $id") }
            .toResponse()

    override fun findByEmail(email: String): UserResponseDTO =
        userRepository.findByEmail(email)
            ?.toResponse()
            ?: throw NoSuchElementException("User not found with email: $email")

    override fun authenticate(email: String, password: String): UserResponseDTO {
        val user = userRepository.findByEmail(email)
            ?: throw IllegalArgumentException("Invalid email or password")

        if (!passwordEncoder.matches(password, user.hashPassword)) {
            throw IllegalArgumentException("Invalid email or password")
        }

        return user.toResponse()
    }

    override fun register(request: RegisterDTO): UserResponseDTO {
        if (userRepository.findByEmail(request.email) != null) {
            throw IllegalArgumentException("Email already in use: ${request.email}")
        }
        return UserEntity(
            name = request.username,
            email = request.email,
            hashPassword = passwordEncoder.encode(request.password)!!
        )
            .let(userRepository::save)
            .toResponse()
    }

    override fun update(id: Long, request: UserRequestDTO): UserResponseDTO =
        userRepository.findById(id)
            .orElseThrow { NoSuchElementException("User not found with id: $id") }
            .let { existing ->
                UserEntity(
                    id = existing.id,
                    name = request.username,
                    email = request.email,
                    hashPassword = existing.hashPassword
                )
            }
            .let(userRepository::save)
            .toResponse()

    override fun delete(id: Long) =
        userRepository.findById(id)
            .orElseThrow { NoSuchElementException("User not found with id: $id") }
            .let(userRepository::delete)

    private fun UserEntity.toResponse() = UserResponseDTO(
        id = id!!,
        username = name,
        email = email
    )
}