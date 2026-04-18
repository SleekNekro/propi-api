package com.propi.propiapi.controllers

import com.propi.propiapi.services.HabitService
import com.propi.shared.dto.request.HabitRequestDTO
import com.propi.shared.dto.response.HabitResponseDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users/{userId}/habits")
class HabitController(
    private val habitService: HabitService
) {
    @GetMapping
    fun findAll(@PathVariable userId: Long): ResponseEntity<List<HabitResponseDTO>> =
        ResponseEntity.ok(habitService.findAll(userId))

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<HabitResponseDTO> =
        ResponseEntity.ok(habitService.findById(id))

    @PostMapping
    fun create(
        @PathVariable userId: Long,
        @RequestBody request: HabitRequestDTO
    ): ResponseEntity<HabitResponseDTO> =
        ResponseEntity.status(HttpStatus.CREATED)
            .body(habitService.create(userId, request))

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: HabitRequestDTO
    ): ResponseEntity<HabitResponseDTO> =
        ResponseEntity.ok(habitService.update(id, request))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        habitService.delete(id)
        return ResponseEntity.noContent().build()
    }
}