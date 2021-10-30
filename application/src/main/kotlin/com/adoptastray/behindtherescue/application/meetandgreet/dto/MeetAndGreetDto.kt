package com.adoptastray.behindtherescue.application.meetandgreet.dto

import com.adoptastray.behindtherescue.application.adoptionevent.dto.AdoptionEventDto
import com.adoptastray.behindtherescue.application.animal.dto.AnimalDto
import com.adoptastray.behindtherescue.domain.animal.entity.Animal
import com.adoptastray.behindtherescue.domain.meetandgreet.entity.MeetAndGreet
import java.time.LocalDate

data class MeetAndGreetDto(
    val id: Int,
    val adoptionEvent: AdoptionEventDto,
    val date: LocalDate,
    val animal: AnimalDto,
    val fullyVaccinated: Boolean,
) {
    constructor(crateReservation: MeetAndGreet, animal: Animal, today: LocalDate) : this(
        crateReservation.id,
        AdoptionEventDto(crateReservation.adoptionEvent, today),
        crateReservation.date,
        AnimalDto(animal),
        crateReservation.fullyVaccinated,
    )
}
