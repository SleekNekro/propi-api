package com.propi.propiapi.controllers

import com.propi.propiapi.services.RestPeriodService
import com.propi.shared.dto.request.RestPeriodRequestDTO
import com.propi.shared.dto.response.RestPeriodResponseDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users/{userId}/rest-periods")
class RestPeriodController(
    private val restPeriodService: RestPeriodService
) {
    @GetMapping
    fun findAll(@PathVariable userId: Long): ResponseEntity<List<RestPeriodResponseDTO>> =
        ResponseEntity.ok(restPeriodService.findAllByUser(userId))

    @GetMapping("/active")
    fun findActive(
        @PathVariable userId: Long,
        @RequestParam date: String
    ): ResponseEntity<List<RestPeriodResponseDTO>> =
        ResponseEntity.ok(restPeriodService.findActiveByDate(userId, date))

    @PostMapping
    fun create(
        @PathVariable userId: Long,
        @RequestBody request: RestPeriodRequestDTO
    ): ResponseEntity<RestPeriodResponseDTO> =
        ResponseEntity.status(HttpStatus.CREATED)
            .body(restPeriodService.create(userId, request))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        restPeriodService.delete(id)
        return ResponseEntity.noContent().build()
    }
}