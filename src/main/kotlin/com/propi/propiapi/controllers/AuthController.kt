package com.propi.propiapi.controllers

import com.propi.propiapi.config.JwtTokenProvider
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
    private val userService: UserService,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @PostMapping("/register")
    fun register(@RequestBody request: RegisterDTO): ResponseEntity<LoginResponse> {
        val user = userService.register(request)
        val token = jwtTokenProvider.generateToken(user.id, user.email)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(LoginResponse(token, user))
    }

    @PostMapping("/login")
    fun login(@RequestBody request: RegisterDTO): ResponseEntity<LoginResponse> {
        val user = userService.authenticate(request.email, request.password)
        val token = jwtTokenProvider.generateToken(user.id, user.email)
        return ResponseEntity.ok(LoginResponse(token, user))
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

data class LoginResponse(
    val token: String,
    val user: UserResponseDTO
)