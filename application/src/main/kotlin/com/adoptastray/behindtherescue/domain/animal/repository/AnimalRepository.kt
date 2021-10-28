package com.adoptastray.behindtherescue.domain.animal.repository

import com.adoptastray.behindtherescue.domain.animal.Species
import com.adoptastray.behindtherescue.domain.animal.entity.Animal
import com.adoptastray.behindtherescue.domain.cratereservation.repository.CrateReservationRepository
import com.adoptastray.petango.AdoptableSearchResponse
import com.adoptastray.petango.WsAdoption
import com.adoptastray.petango.WsAdoptionSoap
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class AnimalRepository(
    val api: AnimalAPI,
    val crateReservationRepository: CrateReservationRepository,
) {
    fun findBySpecies(species: Species): List<Animal> = api.findBySpecies(species)

    fun findAvailableBySpecies(species: Species, date: LocalDate): List<Animal> {
        val animals = api.findBySpecies(species)
        val reservedAnimalIDs = crateReservationRepository.findReservedAnimalIDs(date, species)
        return animals.filter { !reservedAnimalIDs.contains(it.id) }
    }
}