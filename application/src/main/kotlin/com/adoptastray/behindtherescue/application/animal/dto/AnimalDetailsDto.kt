package com.adoptastray.behindtherescue.application.animal.dto

import com.adoptastray.behindtherescue.domain.animal.Sex
import com.adoptastray.behindtherescue.domain.animal.Species
import java.time.LocalDate

data class AnimalDetailsDto(
    val id: Int,
    val name: String,
    val species: Species,
    val sex: Sex,
    val foster: String,
    val photoLinks: List<String>,
    val dateOfBirth: LocalDate,
    val monthsOld: Int,
    val breed: String,
    val description: String,
)