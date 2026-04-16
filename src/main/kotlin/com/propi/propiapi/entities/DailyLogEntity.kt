package com.propi.propiapi.entities

import jakarta.persistence.*

@Entity
@Table(name="daily_log")
class DailyLogEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?=null,

    val date: String,
    var completed: Boolean,
    val karmaPoints: Float
){
    @ManyToOne
    @JoinColumn(name="habit_id")
    val habits: HabitEntity? = null

    @ManyToOne
    @JoinColumn(name = "task_id")
    val task: TaskEntity? = null
}