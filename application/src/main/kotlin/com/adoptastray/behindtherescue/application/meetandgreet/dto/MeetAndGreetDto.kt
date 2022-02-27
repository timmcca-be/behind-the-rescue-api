package com.adoptastray.behindtherescue.application.meetandgreet.dto

import com.adoptastray.behindtherescue.application.adoptionevent.dto.AdoptionEventDto
import com.adoptastray.behindtherescue.application.animal.dto.AnimalDto
import com.adoptastray.behindtherescue.domain.animal.entity.Animal
import com.adoptastray.behindtherescue.domain.meetandgreet.entity.MeetAndGreet
import java.time.Instant
import java.time.LocalDate

data class MeetAndGreetDto(
    val id: Int,
    val adoptionEvent: AdoptionEventDto,
    val date: LocalDate,
    val timestamp: Instant,
    val animal: AnimalDto,
    val potentialAdopterName: String,
    val fullyVaccinated: Boolean,
) {
    constructor(meetAndGreet: MeetAndGreet, animal: Animal, today: LocalDate) : this(
        meetAndGreet.id,
        AdoptionEventDto(meetAndGreet.adoptionEvent, today),
        meetAndGreet.date,
        meetAndGreet.timestamp.toInstant(),
        AnimalDto(animal),
        meetAndGreet.potentialAdopterName,
        meetAndGreet.fullyVaccinated,
    )
}
