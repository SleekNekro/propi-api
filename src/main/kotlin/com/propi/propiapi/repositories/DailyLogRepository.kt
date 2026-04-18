package com.propi.propiapi.repositories

import com.propi.propiapi.entities.DailyLogEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface DailyLogRepository: JpaRepository<DailyLogEntity, Long> {
    fun findAllByHabit_Id(habitId: Long): List<DailyLogEntity>

    @Query("""
        SELECT dl
        FROM DailyLogEntity dl
        WHERE dl.habit.user.id = :userId
        AND dl.date BETWEEN :startDate AND :endDate
        ORDER BY dl.date ASC
    """)
    fun findUserLogsBetweenDates(
        @Param("userId")userId: Long,
        @Param("startDate")startDate: String,
        @Param("endDate")endDate: String,
    ): List<DailyLogEntity>

    fun findByHabit_IdAndDate(habitId: Long, date: String): DailyLogEntity?

    fun existsByHabit_IdAndDate(habitsId: Long, date: String): Boolean
}