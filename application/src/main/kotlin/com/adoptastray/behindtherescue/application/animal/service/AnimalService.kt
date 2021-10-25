package com.adoptastray.behindtherescue.application.animal.service

import com.adoptastray.behindtherescue.application.animal.dto.AnimalDto
import com.adoptastray.behindtherescue.domain.animal.repository.AnimalRepository
import org.springframework.stereotype.Service

@Service
class AnimalService(val animalRepository: AnimalRepository) {
    fun getAll(): List<AnimalDto> = animalRepository.findAll()
        .map { animal -> AnimalDto(animal) }
}