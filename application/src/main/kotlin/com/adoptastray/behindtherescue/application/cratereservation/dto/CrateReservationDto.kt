package com.adoptastray.behindtherescue.application.cratereservation.dto

import com.adoptastray.behindtherescue.application.adoptionevent.dto.AdoptionEventDto
import com.adoptastray.behindtherescue.application.animal.dto.AnimalDto
import com.adoptastray.behindtherescue.domain.animal.entity.Animal
import com.adoptastray.behindtherescue.domain.cratereservation.CrateSize
import com.adoptastray.behindtherescue.domain.cratereservation.entity.CrateReservation
import java.time.LocalDate

data class CrateReservationDto(
    val id: Int,
    val adoptionEvent: AdoptionEventDto,
    val date: LocalDate,
    val animals: List<AnimalDto>,
    val crateSize: CrateSize,
    val fullyVaccinated: Boolean,
) {
    constructor(crateReservation: CrateReservation, animals: Collection<Animal>, today: LocalDate) : this(
        crateReservation.id,
        AdoptionEventDto(crateReservation.adoptionEvent, today),
        crateReservation.date,
        animals.map { AnimalDto(it) },
        crateReservation.crateSize,
        crateReservation.fullyVaccinated,
    )
}