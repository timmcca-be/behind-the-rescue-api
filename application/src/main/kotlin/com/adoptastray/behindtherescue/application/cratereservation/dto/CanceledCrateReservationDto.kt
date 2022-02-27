package com.adoptastray.behindtherescue.application.cratereservation.dto

import com.adoptastray.behindtherescue.domain.cratereservation.entity.CrateReservation
import java.time.LocalDate

data class CanceledCrateReservationDto(
    val id: Int,
    val adoptionEventID: Int,
    val date: LocalDate,
) {
    constructor(crateReservation: CrateReservation) : this(
        crateReservation.id,
        crateReservation.adoptionEvent.id,
        crateReservation.date,
    )
}