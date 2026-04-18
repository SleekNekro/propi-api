package com.propi.propiapi.entities

import jakarta.persistence.*
import jakarta.persistence.CascadeType.*

@Entity
@Table(name = "users")
class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String,
    val email: String,
    val hashPassword: String
) {
    @OneToMany(mappedBy = "user", cascade = [ALL], orphanRemoval = true)
    val habits: MutableList<HabitEntity> = mutableListOf()

    @OneToMany(mappedBy = "user", cascade = [ALL], orphanRemoval = true)
    val tasks: MutableList<TaskEntity> = mutableListOf()

    @OneToMany(mappedBy = "user", cascade = [ALL], orphanRemoval = true)
    val restPeriods: MutableList<RestPeriodEntity> = mutableListOf()
}