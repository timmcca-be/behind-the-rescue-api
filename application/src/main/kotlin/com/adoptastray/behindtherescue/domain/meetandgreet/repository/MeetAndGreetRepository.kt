package com.adoptastray.behindtherescue.domain.meetandgreet.repository

import com.adoptastray.behindtherescue.domain.animal.Species
import com.adoptastray.behindtherescue.domain.meetandgreet.entity.MeetAndGreet
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.time.LocalDate

interface MeetAndGreetRepository : CrudRepository<MeetAndGreet, Int> {
    fun findByAdoptionEventIdAndDate(adoptionEventId: Int, date: LocalDate): Collection<MeetAndGreet>
}