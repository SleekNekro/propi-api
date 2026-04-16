package com.propi.propiapi.services

import com.propi.propiapi.entities.TaskEntity
import com.propi.propiapi.repositories.TaskRepository
import com.propi.propiapi.repositories.UserRepository
import com.propi.shared.dto.request.TaskRequestDTO
import com.propi.shared.dto.response.TaskResponseDTO
import org.springframework.stereotype.Service

interface TaskService {
    fun findAll(userId: Long): List<TaskResponseDTO>
    fun findById(id:Long): TaskResponseDTO
    fun create(userId: Long, request: TaskRequestDTO): TaskResponseDTO
    fun update(id: Long, request: TaskRequestDTO): TaskResponseDTO
    fun delete(id: Long)
}

//Impl
@Service
class TaskServiceImpl(
    private val taskRepository: TaskRepository,
    private val userRepository: UserRepository
): TaskService{
    override fun findAll(userId: Long): List<TaskResponseDTO>
    = taskRepository.findAllByUser_Id(userId)
        .map { entity ->
            TaskResponseDTO(
                id = entity.id!!,
                userId = entity.user.id!!,
                name = entity.name,
                dateTime = entity.dateTime,
                completed = entity.completed
            )
        }

    override fun findById(id: Long): TaskResponseDTO
    = taskRepository.findById(id)
        .orElseThrow { NoSuchElementException("Task not found with id: $id") }
        .let { entity ->
            TaskResponseDTO(
                id = entity.id!!,
                userId = entity.user.id!!,
                name = entity.name,
                dateTime = entity.dateTime,
                completed = entity.completed
            )
        }

    override fun create(
        userId: Long,
        request: TaskRequestDTO
    ): TaskResponseDTO = userRepository.findById(userId)
        .orElseThrow { NoSuchElementException("No user found with id: $userId") }
        .let { user ->
            TaskEntity(
                name = request.name,
                dateTime = request.dateTime,
                completed = false
            ).apply {
                this.user=user
            }
        }
        .let(taskRepository::save)
        .let { saved->
            TaskResponseDTO(
                id = saved.id!!,
                userId = saved.user.id!!,
                name = saved.name,
                dateTime = saved.dateTime,
                completed = saved.completed
            )
        }

    override fun update(
        id: Long,
        request: TaskRequestDTO
    ): TaskResponseDTO =taskRepository.findById(id)
        .orElseThrow { RuntimeException("No task found with id: $id") }
        .let { existing->
            TaskEntity(
                id= existing.id,
                name = request.name,
                dateTime = request.dateTime,
                completed = false
            ).apply {
                this.user=existing.user
            }
        }
        .let ( taskRepository::save)
        .let { saved->
            TaskResponseDTO(
                id = saved.id!!,
                userId = saved.user.id!!,
                name = saved.name,
                dateTime = saved.dateTime,
                completed = saved.completed
            )
        }

    override fun delete(id: Long) =taskRepository.findById(id)
        .orElseThrow { RuntimeException("No task found with id: $id") }
        .let ( taskRepository::delete )

}