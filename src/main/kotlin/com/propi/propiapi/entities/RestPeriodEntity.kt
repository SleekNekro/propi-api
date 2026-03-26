package com.propi.propiapi.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "rest_period")
class RestPeriodEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?=null,

    val dateIni: String,
    val dateEnd: String
){
    @ManyToOne
    @JoinColumn(name = "user_id")
    lateinit var user: UserEntity
}