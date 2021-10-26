package com.adoptastray.behindtherescue.application.cratereservation.dto

import com.adoptastray.behindtherescue.domain.cratestacks.CrateStacks

typealias CrateStackDto = List<CrateDto>

data class EventCrateReservationsDto(
    val crateReservations: List<ListCrateReservationDto>,
    val crateStacks: List<CrateStackDto>,
) {
    constructor(
        crateReservations: List<ListCrateReservationDto>,
        crateStacks: CrateStacks,
    ) : this(
        crateReservations,
        crateStacks.crateStacks.map { crateStack -> crateStack.map { crate -> CrateDto(crate) } }
    )
}