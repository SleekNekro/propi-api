package com.propi.propiapi.repositories

import com.propi.propiapi.entities.RestPeriodEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface RestPeriodRepository: JpaRepository<RestPeriodEntity, Long> {
    fun findAllByUser_Id(userId: Long): List<RestPeriodEntity>

    @Query("""
    SELECT rp
    FROM RestPeriodEntity rp
    WHERE rp.user.id = :userId
      AND rp.dateIni <= :date AND rp.dateEnd >= :date
    """)
    fun findRestPeriodActive_Date(
        @Param("userId")userId: Long,
        @Param("date")date: String,
    ): List<RestPeriodEntity>
}