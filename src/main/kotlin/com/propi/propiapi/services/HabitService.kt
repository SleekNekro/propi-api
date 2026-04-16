package com.propi.propiapi.services

import com.propi.propiapi.entities.HabitEntity
import com.propi.propiapi.repositories.HabitRepository
import com.propi.propiapi.repositories.UserRepository
import com.propi.shared.dto.request.HabitRequestDTO
import com.propi.shared.dto.response.HabitResponseDTO
import org.springframework.stereotype.Service


interface HabitService {
    fun findAll(userId: Long): List<HabitResponseDTO>
    fun findById(id:Long): HabitResponseDTO
    fun create(userId: Long, request: HabitRequestDTO): HabitResponseDTO
    fun update(id: Long, request: HabitRequestDTO): HabitResponseDTO
    fun delete(id: Long)
}

//Impl
@Service
class HabitServiceImpl(
    private val habitRepository: HabitRepository,
    private val userRepository: UserRepository
): HabitService{
    override fun findAll(userId: Long): List<HabitResponseDTO> =
        habitRepository.findAllByUser_Id(userId)
           .map { entity ->
               HabitResponseDTO(
                   id=entity.id!!,
                   userId = entity.user.id!!,
                   name = entity.name,
                   difficulty = entity.difficulty,
                   weekDays = entity.weekDays,
                   duration = entity.duration
               )
           }


    override fun findById(id: Long): HabitResponseDTO  = habitRepository.findById(id)
            .orElseThrow { NoSuchElementException("Habit not found with id: $id") }
        .let { entity ->
            HabitResponseDTO(
                id=entity.id!!,
                userId = entity.user.id!!,
                name = entity.name,
                difficulty = entity.difficulty,
                weekDays = entity.weekDays,
                duration = entity.duration
            )
        }

    override fun create(
        userId: Long,
        request: HabitRequestDTO
    ): HabitResponseDTO = userRepository.findById(userId)
            .orElseThrow { RuntimeException("No user found with id: $userId") }
            .let { user->
                HabitEntity(
                    name = request.name,
                    difficulty = request.difficulty,
                    weekDays = request.weekDays,
                    duration = request.duration
                ).apply{
                    this.user= user
                }
            }
            .let (habitRepository::save)
        .let { saved ->
            HabitResponseDTO(
                id = saved.id!!,
                userId = saved.user.id!!,
                name = saved.name,
                difficulty = saved.difficulty,
                weekDays = saved.weekDays,
                duration = saved.duration
            )
        }


    override fun update(
        id: Long,
        request: HabitRequestDTO
    ): HabitResponseDTO = habitRepository.findById(id)
        .orElseThrow { RuntimeException("No habit found with id: $id") }
        .let { existing ->
            HabitEntity(
                id = existing.id,
                name = request.name,
                difficulty = request.difficulty,
                weekDays = request.weekDays,
                duration = request.duration
            ).apply {
                this.user = existing.user
            }
        }
        .let(habitRepository::save)
        .let { saved->
            HabitResponseDTO(
                id = saved.id!!,
                userId = saved.user.id!!,
                name = saved.name,
                difficulty = saved.difficulty,
                weekDays = saved.weekDays,
                duration = saved.duration
            )
        }

    override fun delete(id: Long) =
        habitRepository.findById(id)
            .orElseThrow { RuntimeException("No habit found with id: $id") }
            .let(habitRepository::delete)

}