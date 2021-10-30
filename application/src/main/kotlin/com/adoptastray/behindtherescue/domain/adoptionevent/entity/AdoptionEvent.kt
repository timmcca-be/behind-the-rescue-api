package com.adoptastray.behindtherescue.domain.adoptionevent.entity

import com.adoptastray.behindtherescue.domain.animal.Species
import com.adoptastray.behindtherescue.domain.animal.entity.Animal
import com.adoptastray.behindtherescue.domain.cratereservation.CrateSize
import com.adoptastray.behindtherescue.domain.cratereservation.entity.CrateReservation
import com.adoptastray.behindtherescue.domain.meetandgreet.entity.MeetAndGreet
import java.time.DayOfWeek
import java.time.LocalDate
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
) {
    private fun validateDate(date: LocalDate) =
        require(date.dayOfWeek == dayOfWeek) { "This event occurs on ${dayOfWeek}s; you cannot reserve a crate for it on a ${date.dayOfWeek}" }

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

    fun scheduleMeetAndGreet(date: LocalDate, animal: Animal, fullyVaccinated: Boolean): MeetAndGreet {
        validateDate(date)
        validateAnimal(animal)
        return MeetAndGreet(
            adoptionEvent = this,
            date = date,
            animalID = animal.id,
            fullyVaccinated = fullyVaccinated
        )
    }
}