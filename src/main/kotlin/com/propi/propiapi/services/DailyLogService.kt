package com.propi.propiapi.services

import com.propi.propiapi.repositories.DailyLogRepository
import com.propi.shared.dto.response.DailyLogDTO
import org.springframework.stereotype.Service


interface DailyLogService {
    fun findUserLogsBetweenDates(
        userId: Long,
        startDate: String,
        endDate: String
    ): List<DailyLogDTO>
    fun markCompleted(habitId: Long, date: String, completed: Boolean)
}

//IMPL
@Service
class DailyLogServiceImpl(
    private val dailyLogRepository: DailyLogRepository,
) : DailyLogService{
    override fun findUserLogsBetweenDates(
        userId: Long,
        startDate: String,
        endDate: String
    ): List<DailyLogDTO> =
        dailyLogRepository.findUserLogsBetweenDates(userId, startDate, endDate)
            .map { entity ->
                DailyLogDTO(
                    id = entity.id!!,
                    date = entity.date,
                    completed = entity.completed,
                    karmaPoints = entity.karmaPoints,
                    habitId = entity.habits?.id,
                    taskId = entity.task?.id
                )
            }

    override fun markCompleted(habitId: Long, date: String, completed: Boolean) {
        val log = dailyLogRepository.findByHabits_IdAndDate(habitId, date)
            ?: throw RuntimeException("DailyLog not found")

        log.completed = completed

        dailyLogRepository.save(log)
    }
}