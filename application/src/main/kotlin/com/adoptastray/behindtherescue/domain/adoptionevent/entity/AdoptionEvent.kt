package com.adoptastray.behindtherescue.domain.adoptionevent.entity

import com.adoptastray.behindtherescue.domain.animal.Species
import com.adoptastray.behindtherescue.domain.animal.entity.Animal
import com.adoptastray.behindtherescue.domain.cratereservation.CrateSize
import com.adoptastray.behindtherescue.domain.cratereservation.entity.CrateReservation
import com.adoptastray.behindtherescue.domain.meetandgreet.entity.MeetAndGreet
import org.hibernate.annotations.ColumnDefault
import java.sql.Timestamp
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import javax.persistence.Entity;
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity(name = "AdoptionEvent")
@Table(name = "adoption_events")
data class AdoptionEvent(
    @Id @GeneratedValue val id: Int = 0,
    @NotNull val name: String,
    @NotNull val location: String,
    @NotNull val availableSpecies: Species,
    @NotNull val dayOfWeek: DayOfWeek,
    @NotNull @ColumnDefault("'America/New_York'") val timeZone: ZoneId,
) {
    private fun validateDate(date: LocalDate) =
        require(date.dayOfWeek == dayOfWeek) { "This event occurs on ${dayOfWeek}s; the specified date is a ${date.dayOfWeek}" }

    private fun validateAnimal(animal: Animal) =
        require(animal.species == availableSpecies) { "${animal.name} is a ${animal.species}, which cannot be brought to an event for ${availableSpecies}s" }

    fun nextOccurrenceDate(today: LocalDate): LocalDate = today.with(TemporalAdjusters.nextOrSame(dayOfWeek))

    fun reserveCrate(date: LocalDate, animals: Collection<Animal>, crateSize: CrateSize, fullyVaccinated: Boolean): CrateReservation {
        validateDate(date)
        for (animal in animals) {
            validateAnimal(animal)
        }
        return CrateReservation(
            adoptionEvent = this,
            date = date,
            animalIDs = animals.map { it.id }.toSet(),
            crateSize = crateSize,
            fullyVaccinated = fullyVaccinated,
        )
    }

    fun scheduleMeetAndGreet(
        date: LocalDate,
        timestamp: Instant,
        animal: Animal,
        potentialAdopterName: String,
        fullyVaccinated: Boolean,
    ): MeetAndGreet {
        validateDate(date)
        val timestampDate = timestamp.atZone(timeZone).toLocalDate()
        require(date == timestampDate) { "The specified timestamp is not on $date in ${timeZone.id}" }
        validateAnimal(animal)
        return MeetAndGreet(
            adoptionEvent = this,
            date = date,
            timestamp = Timestamp.from(timestamp),
            animalID = animal.id,
            potentialAdopterName = potentialAdopterName,
            fullyVaccinated = fullyVaccinated
        )
    }
}