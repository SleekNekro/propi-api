package com.propi.propiapi.entities

import com.propi.shared.model.Difficulty
import com.propi.shared.model.Week
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name="habits")
class HabitEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?=null,

    val name: String,
    val difficulty: Difficulty,
    @ElementCollection
    @Enumerated(EnumType.STRING)
    val weekDays: List<Week>,
    val duration: String?
){
    @ManyToOne
    @JoinColumn(name = "user_id")
    lateinit var user: UserEntity
}
