package com.propi.propiapi.controllers

import com.propi.propiapi.services.DailyLogService
import com.propi.shared.dto.response.DailyLogDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users/{userId}/dailylogs")
class DailyLogController(
    private val dailyLogService: DailyLogService
) {
    @GetMapping
    fun findAll(
        @PathVariable userId: Long,
        @RequestParam startDate: String,
        @RequestParam endDate: String
    ): ResponseEntity<List<DailyLogDTO>> =
        ResponseEntity.ok(
            dailyLogService.findUserLogsBetweenDates(userId, startDate, endDate)
        )

    @PatchMapping("/{habitId}/completed")
    fun markCompleted(
        @PathVariable userId: Long,
        @PathVariable habitId: Long,
        @RequestParam date: String,
        @RequestParam completed: Boolean
    ): ResponseEntity<Unit> {
        dailyLogService.markCompleted(habitId, date, completed)
        return ResponseEntity.ok().build()
    }
}