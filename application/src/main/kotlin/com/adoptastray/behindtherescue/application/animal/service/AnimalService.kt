package com.adoptastray.behindtherescue.application.animal.service

import com.adoptastray.behindtherescue.application.animal.dto.AnimalDto
import com.adoptastray.behindtherescue.domain.animal.Species
import com.adoptastray.behindtherescue.domain.animal.repository.AnimalRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AnimalService(val animalRepository: AnimalRepository) {
    fun getAvailableAnimals(species: Species, date: LocalDate): List<AnimalDto> =
        animalRepository.findAvailableBySpecies(species, date)
            .map { AnimalDto(it) }
}