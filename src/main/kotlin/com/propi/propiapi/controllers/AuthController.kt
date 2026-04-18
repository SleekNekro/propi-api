package com.propi.propiapi.controllers

import com.propi.propiapi.services.UserService
import com.propi.shared.dto.RegisterDTO
import com.propi.shared.dto.request.UserRequestDTO
import com.propi.shared.dto.response.UserResponseDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userService: UserService
) {
    @PostMapping("/register")
    fun register(@RequestBody request: RegisterDTO): ResponseEntity<UserResponseDTO> =
        ResponseEntity.status(HttpStatus.CREATED)
            .body(userService.register(request))

    @PostMapping("/login")
    fun login(@RequestBody request: RegisterDTO): ResponseEntity<UserResponseDTO> {
        val user = userService.findByEmail(request.email)
        return ResponseEntity.ok(user)
    }

    @GetMapping("/users/{id}")
    fun getUser(@PathVariable id: Long): ResponseEntity<UserResponseDTO> =
        ResponseEntity.ok(userService.findById(id))

    @PutMapping("/users/{id}")
    fun updateUser(
        @PathVariable id: Long,
        @RequestBody request: UserRequestDTO
    ): ResponseEntity<UserResponseDTO> =
        ResponseEntity.ok(userService.update(id, request))

    @DeleteMapping("/users/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Unit> {
        userService.delete(id)
        return ResponseEntity.noContent().build()
    }
}