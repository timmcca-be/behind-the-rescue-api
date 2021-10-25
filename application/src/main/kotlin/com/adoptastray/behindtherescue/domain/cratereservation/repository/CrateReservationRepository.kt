package com.adoptastray.behindtherescue.domain.cratereservation.repository

import com.adoptastray.behindtherescue.domain.cratereservation.entity.CrateReservation
import org.springframework.data.repository.CrudRepository
import java.time.LocalDate

interface CrateReservationRepository : CrudRepository<CrateReservation, Int> {
    fun findByAdoptionEventIdAndDate(adoptionEventId: Int, date: LocalDate): Collection<CrateReservation>
}