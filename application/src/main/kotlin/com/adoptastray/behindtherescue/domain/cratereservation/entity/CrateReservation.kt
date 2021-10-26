package com.adoptastray.behindtherescue.domain.cratereservation.entity

import com.adoptastray.behindtherescue.domain.adoptionevent.entity.AdoptionEvent
import com.adoptastray.behindtherescue.domain.cratereservation.CrateSize
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity(name = "crate_reservations")
data class CrateReservation(
    @Id @GeneratedValue val id: Int = 0,
    @ManyToOne val adoptionEvent: AdoptionEvent,
    @NotNull val date: LocalDate,
    @NotNull @NotEmpty @ElementCollection val animalIDs: Set<Int>,
    @NotNull val crateSize: CrateSize,
    val fullyVaccinated: Boolean,
)
