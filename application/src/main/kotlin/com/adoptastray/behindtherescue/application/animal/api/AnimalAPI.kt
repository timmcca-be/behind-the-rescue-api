package com.adoptastray.behindtherescue.application.animal.api

import com.adoptastray.behindtherescue.application.animal.dto.AnimalDetailsDto

interface AnimalAPI {
    fun getAnimalDetails(animalID: Int): AnimalDetailsDto
}