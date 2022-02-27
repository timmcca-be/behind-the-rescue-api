package com.adoptastray.behindtherescue.web.meetandgreet.message

import java.time.Instant

data class ScheduleMeetAndGreetRequest(
    val timestamp: Instant,
    val animalID: Int,
    val potentialAdopterName: String,
    val fullyVaccinated: Boolean,
)
