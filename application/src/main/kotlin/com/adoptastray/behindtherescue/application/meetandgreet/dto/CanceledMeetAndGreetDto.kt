package com.adoptastray.behindtherescue.application.meetandgreet.dto

import com.adoptastray.behindtherescue.domain.meetandgreet.entity.MeetAndGreet
import java.time.LocalDate

data class CanceledMeetAndGreetDto(
    val id: Int,
    val adoptionEventID: Int,
    val date: LocalDate,
) {
    constructor(meetAndGreet: MeetAndGreet) : this(
        meetAndGreet.id,
        meetAndGreet.adoptionEvent.id,
        meetAndGreet.date,
    )
}