package com.adoptastray.behindtherescue.web.meetandgreet.message

data class ScheduleMeetAndGreetRequest(
    val animalID: Int,
    val fullyVaccinated: Boolean,
)
