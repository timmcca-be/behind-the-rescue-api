package com.adoptastray.behindtherescue.application.adoptionevent.dto

import com.adoptastray.behindtherescue.domain.adoptionevent.entity.AdoptionEvent
import com.adoptastray.behindtherescue.domain.animal.Species
import java.time.DayOfWeek
import java.time.LocalDate

data class AdoptionEventDto(
    val id: Int,
    val name: String,
    val location: String,
    val availableSpecies: Species,
    val dayOfWeek: DayOfWeek,
    val nextOccurrenceDate: LocalDate,
    val timeZone: String,
) {
    constructor(adoptionEvent: AdoptionEvent, today: LocalDate) : this(
        adoptionEvent.id,
        adoptionEvent.name,
        adoptionEvent.location,
        adoptionEvent.availableSpecies,
        adoptionEvent.dayOfWeek,
        adoptionEvent.nextOccurrenceDate(today),
        adoptionEvent.timeZone.id,
    )
}
