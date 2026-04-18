package com.propi.propiapi.entities

import jakarta.persistence.*

@Entity
@Table(name = "daily_log")
class DailyLogEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val date: String,
    var completed: Boolean,
    val karmaPoints: Float
) {
    @ManyToOne
    @JoinColumn(name = "habit_id")
    val habit: HabitEntity? = null

    @ManyToOne
    @JoinColumn(name = "task_id")
    val task: TaskEntity? = null

    @ManyToOne
    @JoinColumn(name = "user_id")
    lateinit var user: UserEntity
}