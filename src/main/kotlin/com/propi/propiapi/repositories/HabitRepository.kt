package com.propi.propiapi.repositories

import com.propi.propiapi.entities.HabitEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HabitRepository: JpaRepository<HabitEntity, Long> {
    fun findAllByUser_Id(userId: Long): List<HabitEntity>

    fun existsByUser_IdAndName(userId: Long, name: String): Boolean
}