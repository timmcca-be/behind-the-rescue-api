package com.adoptastray.behindtherescue.web.adoptionevent.message

import com.adoptastray.behindtherescue.domain.animal.Species
import java.time.DayOfWeek

data class CreateAdoptionEventRequest(
    val name: String,
    val location: String,
    val availableSpecies: Species,
    val dayOfWeek: DayOfWeek,
)