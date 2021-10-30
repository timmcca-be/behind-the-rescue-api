package com.adoptastray.behindtherescue.application.meetandgreet.dto

import com.adoptastray.behindtherescue.application.adoptionevent.dto.AdoptionEventDto
import com.adoptastray.behindtherescue.application.animal.dto.AnimalDto
import com.adoptastray.behindtherescue.domain.animal.entity.Animal
import com.adoptastray.behindtherescue.domain.meetandgreet.entity.MeetAndGreet
import java.time.LocalDate

data class EventMeetAndGreetDto(
    val id: Int,
    val date: LocalDate,
    val animal: AnimalDto?,
    val fullyVaccinated: Boolean,
) {
    constructor(meetAndGreet: MeetAndGreet, animal: Animal?) : this(
        meetAndGreet.id,
        meetAndGreet.date,
        if (animal == null) null else AnimalDto(animal),
        meetAndGreet.fullyVaccinated,
    )
}
