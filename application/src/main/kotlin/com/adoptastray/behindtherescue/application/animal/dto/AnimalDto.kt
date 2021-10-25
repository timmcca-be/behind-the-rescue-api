package com.adoptastray.behindtherescue.application.animal.dto

import com.adoptastray.behindtherescue.domain.animal.Species
import com.adoptastray.behindtherescue.domain.animal.entity.Animal

data class AnimalDto(
    val id: Int,
    val name: String,
    val species: Species,
) {
    constructor(animal: Animal) : this(
        animal.id,
        animal.name,
        animal.species,
    )
}
