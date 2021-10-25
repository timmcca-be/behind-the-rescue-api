package com.adoptastray.behindtherescue.web.cratereservation.resource

import com.adoptastray.behindtherescue.application.cratereservation.service.CrateReservationService
import com.adoptastray.behindtherescue.web.cratereservation.message.GetCrateReservationsResponse
import com.adoptastray.behindtherescue.web.cratereservation.message.ReserveCrateRequest
import com.adoptastray.behindtherescue.web.cratereservation.message.ReserveCrateResponse
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/adoption-events/{adoptionEventID}/dates/{date}/crate-reservations")
class CrateReservationResource(val service: CrateReservationService) {
    @GetMapping
    fun get(
        @PathVariable adoptionEventID: Int,
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate,
    ): GetCrateReservationsResponse = GetCrateReservationsResponse(service.getByEvent(adoptionEventID, date))

    @PostMapping
    fun reserve(
        @PathVariable adoptionEventID: Int,
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate,
        @RequestBody request: ReserveCrateRequest
    ): ReserveCrateResponse = ReserveCrateResponse(service.reserve(
        adoptionEventID,
        date,
        request.animalIDs,
        request.crateSize,
        request.fullyVaccinated,
    ))
}