package com.adoptastray.behindtherescue.application.common

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.ZoneId

@Component
class DateProvider {
    @Value("\${behind-the-rescue.time-zone}")
    private lateinit var timeZoneID: String;

    val today get() = LocalDate.now(ZoneId.of(timeZoneID))
}