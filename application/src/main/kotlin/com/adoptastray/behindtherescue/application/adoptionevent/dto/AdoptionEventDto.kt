package com.adoptastray.behindtherescue.application.adoptionevent.dto

import com.adoptastray.behindtherescue.domain.adoptionevent.entity.AdoptionEvent
import com.adoptastray.behindtherescue.domain.animal.Species
import java.time.DayOfWeek

data class AdoptionEventDto(
    val id: Int,
    val name: String,
    val availableSpecies: Species,
    val dayOfWeek: DayOfWeek,
) {
    constructor(adoptionEvent: AdoptionEvent) : this(
        adoptionEvent.id,
        adoptionEvent.name,
        adoptionEvent.availableSpecies,
        adoptionEvent.dayOfWeek)
}
