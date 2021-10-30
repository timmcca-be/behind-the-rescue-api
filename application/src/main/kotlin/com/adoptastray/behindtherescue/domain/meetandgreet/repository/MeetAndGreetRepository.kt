package com.adoptastray.behindtherescue.domain.meetandgreet.repository

import com.adoptastray.behindtherescue.domain.animal.Species
import com.adoptastray.behindtherescue.domain.meetandgreet.entity.MeetAndGreet
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.time.LocalDate

interface MeetAndGreetRepository : CrudRepository<MeetAndGreet, Int> {
    fun findByAdoptionEventIdAndDate(adoptionEventId: Int, date: LocalDate): Collection<MeetAndGreet>

    @Query("""
        select m.animalID
        from MeetAndGreet m
        where m.adoptionEvent.availableSpecies = :species
            and m.date = :date
    """)
    fun findAnimalIDsWithMeetAndGreet(date: LocalDate, species: Species): Set<Int>

    @Query("""
        select
            case when count(m) > 0 then true else false end
        from MeetAndGreet m
        where m.animalID = :animalID
            and m.date = :date
    """)
    fun existsByAnimalIDAndDate(animalID: Int, date: LocalDate): Boolean
}