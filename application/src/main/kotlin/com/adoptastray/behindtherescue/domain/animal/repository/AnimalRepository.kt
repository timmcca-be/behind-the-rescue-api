package com.adoptastray.behindtherescue.domain.animal.repository

import com.adoptastray.behindtherescue.domain.animal.Species
import com.adoptastray.behindtherescue.domain.animal.entity.Animal
import java.time.LocalDate

interface AnimalRepository {
    fun findAll(): List<Animal>
    fun findBySpecies(species: Species): List<Animal>
    fun findAvailableBySpecies(species: Species, date: LocalDate): List<Animal>
}