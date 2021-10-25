package com.adoptastray.behindtherescue.application.cratereservation.dto

import com.adoptastray.behindtherescue.application.animal.dto.AnimalDto
import com.adoptastray.behindtherescue.domain.animal.entity.Animal
import com.adoptastray.behindtherescue.domain.cratereservation.CrateSize
import com.adoptastray.behindtherescue.domain.cratereservation.entity.CrateReservation
import java.time.LocalDate

data class ListCrateReservationDto(
    val id: Int,
    val animals: List<AnimalDto>,
    val crateSize: CrateSize,
    val fullyVaccinated: Boolean,
) {
    constructor(crateReservation: CrateReservation, animals: Collection<Animal>) : this(
        crateReservation.id,
        animals.map { animal -> AnimalDto(animal) },
        crateReservation.crateSize,
        crateReservation.fullyVaccinated,
    )
}