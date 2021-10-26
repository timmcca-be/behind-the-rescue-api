package com.adoptastray.behindtherescue.application.cratereservation.dto

import com.adoptastray.behindtherescue.domain.cratestacks.CrateStack
import com.adoptastray.behindtherescue.domain.cratestacks.CrateStacks

data class EventCrateReservationsDto(
    val crateReservations: List<ListCrateReservationDto>,
    val crateStacks: List<CrateStack>,
) {
    constructor(
        crateReservations: List<ListCrateReservationDto>,
        crateStacks: CrateStacks,
    ) : this(
        crateReservations,
        crateStacks.crateStacks,
    )
}