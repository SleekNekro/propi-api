package com.propi.propiapi.services

import com.propi.propiapi.entities.RestPeriodEntity
import com.propi.propiapi.repositories.RestPeriodRepository
import com.propi.propiapi.repositories.UserRepository
import com.propi.shared.dto.request.RestPeriodRequestDTO
import com.propi.shared.dto.response.RestPeriodResponseDTO
import org.springframework.stereotype.Service

interface RestPeriodService {
    fun findAllByUser(userId: Long): List<RestPeriodResponseDTO>
    fun findActiveByDate(userId: Long, date: String): List<RestPeriodResponseDTO>
    fun create(userId: Long, request: RestPeriodRequestDTO): RestPeriodResponseDTO
    fun delete(id: Long)
}

@Service
class RestPeriodServiceImpl(
    private val restPeriodRepository: RestPeriodRepository,
    private val userRepository: UserRepository
) : RestPeriodService {

    override fun findAllByUser(userId: Long): List<RestPeriodResponseDTO> =
        restPeriodRepository.findAllByUser_Id(userId)
            .map { it.toResponse() }

    override fun findActiveByDate(userId: Long, date: String): List<RestPeriodResponseDTO> =
        restPeriodRepository.findRestPeriodActive_Date(userId, date)
            .map { it.toResponse() }

    override fun create(userId: Long, request: RestPeriodRequestDTO): RestPeriodResponseDTO =
        userRepository.findById(userId)
            .orElseThrow { NoSuchElementException("User not found with id: $userId") }
            .let { user ->
                RestPeriodEntity(
                    dateIni = request.dateIni,
                    dateEnd = request.dateEnd
                ).apply {
                    this.user = user
                }
            }
            .let(restPeriodRepository::save)
            .toResponse()

    override fun delete(id: Long) =
        restPeriodRepository.findById(id)
            .orElseThrow { NoSuchElementException("RestPeriod not found with id: $id") }
            .let(restPeriodRepository::delete)

    private fun RestPeriodEntity.toResponse() = RestPeriodResponseDTO(
        id = id!!,
        userId = user.id!!,
        dateIni = dateIni,
        dateEnd = dateEnd
    )
}