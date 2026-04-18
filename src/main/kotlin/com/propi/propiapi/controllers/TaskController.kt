package com.propi.propiapi.controllers

import com.propi.propiapi.services.TaskService
import com.propi.shared.dto.request.TaskRequestDTO
import com.propi.shared.dto.response.TaskResponseDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users/{userId}/tasks")
class TaskController(
    private val taskService: TaskService
) {
    @GetMapping
    fun findAll(@PathVariable userId: Long): ResponseEntity<List<TaskResponseDTO>> =
        ResponseEntity.ok(taskService.findAll(userId))

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<TaskResponseDTO> =
        ResponseEntity.ok(taskService.findById(id))

    @PostMapping
    fun create(
        @PathVariable userId: Long,
        @RequestBody request: TaskRequestDTO
    ): ResponseEntity<TaskResponseDTO> =
        ResponseEntity.status(HttpStatus.CREATED)
            .body(taskService.create(userId, request))

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: TaskRequestDTO
    ): ResponseEntity<TaskResponseDTO> =
        ResponseEntity.ok(taskService.update(id, request))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        taskService.delete(id)
        return ResponseEntity.noContent().build()
    }
}