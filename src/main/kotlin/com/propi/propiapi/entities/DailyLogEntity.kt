package com.propi.propiapi.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name="daily_log")
class DailyLogEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?=null,

    val date: String,
    val completed: Boolean,
    val karmaPoints: Float
){
    @ManyToOne
    @JoinColumn(name="habit_id")
    val habits: HabitEntity? = null

    @ManyToOne
    @JoinColumn(name = "task_id")
    val task: TaskEntity? = null
}