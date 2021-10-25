package com.adoptastray.behindtherescue.domain.adoptionevent.entity

import com.adoptastray.behindtherescue.domain.animal.Species
import com.adoptastray.behindtherescue.domain.animal.entity.Animal
import com.adoptastray.behindtherescue.domain.cratereservation.CrateSize
import com.adoptastray.behindtherescue.domain.cratereservation.entity.CrateReservation
import java.time.DayOfWeek
import java.time.LocalDate
import javax.persistence.Entity;
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotNull

@Entity(name = "adoption_event")
data class AdoptionEvent(
    @Id @GeneratedValue val id: Int = 0,
    @NotNull val name: String,
    @NotNull val availableSpecies: Species,
    @NotNull val dayOfWeek: DayOfWeek,
) {
    fun reserveCrate(date: LocalDate, animals: Collection<Animal>, crateSize: CrateSize, fullyVaccinated: Boolean): CrateReservation {
        require(date.dayOfWeek == dayOfWeek) { "This event occurs on ${dayOfWeek}s; you cannot reserve a crate for it on a ${date.dayOfWeek}" }
        for (animal in animals) {
            require(animal.species == availableSpecies) { "${animal.name} is a ${animal.species}, which cannot be brought to an event for ${availableSpecies}s" }
        }
        return CrateReservation(
            adoptionEvent = this,
            date = date,
            animalIDs = animals.map { animal -> animal.id }.toSet(),
            crateSize = crateSize,
            fullyVaccinated = fullyVaccinated,
        )
    }
}