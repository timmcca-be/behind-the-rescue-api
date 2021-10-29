package com.adoptastray.behindtherescue.infrastructure.animal.repository

import com.adoptastray.behindtherescue.domain.animal.Species
import com.adoptastray.behindtherescue.domain.animal.entity.Animal
import com.adoptastray.behindtherescue.application.animal.api.AnimalAPI
import com.adoptastray.behindtherescue.domain.animal.repository.AnimalRepository
import com.adoptastray.behindtherescue.domain.cratereservation.repository.CrateReservationRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class PetangoAnimalRepository(
    val api: PetangoAnimalAPI,
    val crateReservationRepository: CrateReservationRepository,
) : AnimalRepository {
    override fun findAll(): List<Animal> = api.findAll()

    override fun findBySpecies(species: Species): List<Animal> = api.findBySpecies(species)

    override fun findAvailableBySpecies(species: Species, date: LocalDate): List<Animal> {
        val animals = api.findBySpecies(species)
        val reservedAnimalIDs = crateReservationRepository.findReservedAnimalIDs(date, species)
        return animals.filter { !reservedAnimalIDs.contains(it.id) }
    }
}