package com.propi.propiapi.entities

import com.propi.shared.model.Difficulty
import com.propi.shared.model.Week
import jakarta.persistence.*

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
    val duration: Int?
){
    @ManyToOne
    @JoinColumn(name = "user_id")
    lateinit var user: UserEntity
}
