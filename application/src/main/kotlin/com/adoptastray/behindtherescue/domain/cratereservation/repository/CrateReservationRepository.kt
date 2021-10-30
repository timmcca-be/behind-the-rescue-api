package com.adoptastray.behindtherescue.domain.cratereservation.repository

import com.adoptastray.behindtherescue.domain.animal.Species
import com.adoptastray.behindtherescue.domain.cratereservation.entity.CrateReservation
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.time.LocalDate

interface CrateReservationRepository : CrudRepository<CrateReservation, Int> {
    fun findByAdoptionEventIdAndDate(adoptionEventId: Int, date: LocalDate): Collection<CrateReservation>

    @Query("""
        select a
        from CrateReservation c
        inner join c.animalIDs a
        where c.adoptionEvent.availableSpecies = :species
            and c.date = :date
    """)
    fun findAnimalIDsWithReservation(date: LocalDate, species: Species): Set<Int>

    @Query("""
        select
            case when count(c) > 0 then true else false end
        from CrateReservation c
        inner join c.animalIDs a
        where a = :animalID
            and c.date = :date
    """)
    fun existsByAnimalIDAndDate(animalID: Int, date: LocalDate): Boolean
}