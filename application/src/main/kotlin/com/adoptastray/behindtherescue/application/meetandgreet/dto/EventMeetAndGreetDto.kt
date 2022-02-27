package com.adoptastray.behindtherescue.application.meetandgreet.dto

import com.adoptastray.behindtherescue.application.animal.dto.AnimalDto
import com.adoptastray.behindtherescue.domain.animal.entity.Animal
import com.adoptastray.behindtherescue.domain.meetandgreet.entity.MeetAndGreet
import java.time.Instant
import java.time.LocalDate

data class EventMeetAndGreetDto(
    val id: Int,
    val date: LocalDate,
    val timestamp: Instant,
    val animal: AnimalDto?,
    val potentialAdopterName: String,
    val fullyVaccinated: Boolean,
) {
    constructor(meetAndGreet: MeetAndGreet, animal: Animal?) : this(
        meetAndGreet.id,
        meetAndGreet.date,
        meetAndGreet.timestamp.toInstant(),
        if (animal == null) null else AnimalDto(animal),
        meetAndGreet.potentialAdopterName,
        meetAndGreet.fullyVaccinated,
    )
}
