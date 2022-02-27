package com.adoptastray.behindtherescue.web.cratereservation.resource

import com.adoptastray.behindtherescue.application.cratereservation.dto.EventCrateReservationsDto
import com.adoptastray.behindtherescue.application.cratereservation.service.CrateReservationService
import com.adoptastray.behindtherescue.web.cratereservation.message.CancelCrateReservationResponse
import com.adoptastray.behindtherescue.web.cratereservation.message.ReserveCrateRequest
import com.adoptastray.behindtherescue.web.cratereservation.message.ReserveCrateResponse
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
class CrateReservationResource(val service: CrateReservationService) {
    @GetMapping("/adoption-events/{adoptionEventID}/dates/{date}/crate-reservations")
    fun get(
        @PathVariable adoptionEventID: Int,
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate,
    ): EventCrateReservationsDto = service.getByEvent(adoptionEventID, date)

    @PostMapping("/adoption-events/{adoptionEventID}/dates/{date}/crate-reservations")
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

    @DeleteMapping("/crate-reservations/{crateReservationID}")
    fun cancel(
        @PathVariable crateReservationID: Int,
    ): CancelCrateReservationResponse = CancelCrateReservationResponse(
        service.cancel(crateReservationID),
    )
}
