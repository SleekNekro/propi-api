package com.propi.propiapi.repositories

import com.propi.propiapi.entities.TaskEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository: JpaRepository<TaskEntity, Long> {
    fun findAllByUser_Id(userId: Long): List<TaskEntity>

    fun findAllByUser_IdAndCompletedFalse(userId: Long): List<TaskEntity>
}