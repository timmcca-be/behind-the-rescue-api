package com.adoptastray.behindtherescue.application.common

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.ZoneId

@Component
class DateProvider {
    fun today(timeZone: ZoneId): LocalDate = LocalDate.now(timeZone)
}