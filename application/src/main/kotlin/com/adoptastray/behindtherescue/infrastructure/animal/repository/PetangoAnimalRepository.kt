package com.adoptastray.behindtherescue.infrastructure.animal.repository

import com.adoptastray.behindtherescue.domain.animal.Species
import com.adoptastray.behindtherescue.domain.animal.entity.Animal
import com.adoptastray.behindtherescue.application.animal.api.AnimalAPI
import com.adoptastray.behindtherescue.domain.animal.repository.AnimalRepository
import com.adoptastray.behindtherescue.domain.cratereservation.repository.CrateReservationRepository
import com.adoptastray.behindtherescue.domain.meetandgreet.repository.MeetAndGreetRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class PetangoAnimalRepository(
    val api: PetangoAnimalAPI,
    val crateReservationRepository: CrateReservationRepository,
    val meetAndGreetRepository: MeetAndGreetRepository,
) : AnimalRepository {
    override fun findAll(): List<Animal> = api.findAll()

    override fun findAvailableByID(animalID: Int, date: LocalDate): Animal? {
        if (crateReservationRepository.existsByAnimalIDAndDate(animalID, date)
            || meetAndGreetRepository.existsByAnimalIDAndDate(animalID, date)
        ) {
            return null
        }
        return api.findAll().find { it.id == animalID } ?: return null
    }

    override fun findBySpecies(species: Species): List<Animal> = api.findBySpecies(species)

    override fun findAvailableBySpecies(species: Species, date: LocalDate): List<Animal> {
        val animals = api.findBySpecies(species)
        val crateAnimalIDs = crateReservationRepository.findAnimalIDsWithReservation(date, species)
        val meetAndGreetAnimalIDs = meetAndGreetRepository.findAnimalIDsWithMeetAndGreet(date, species)
        val reservedAnimalIDs = crateAnimalIDs.union(meetAndGreetAnimalIDs)
        return animals.filter { !reservedAnimalIDs.contains(it.id) }
    }
}