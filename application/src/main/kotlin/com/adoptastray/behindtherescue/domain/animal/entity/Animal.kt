package com.adoptastray.behindtherescue.domain.animal.entity

import com.adoptastray.behindtherescue.domain.animal.Species
import com.adoptastray.petango.AdoptableDetailsResponse

data class Animal(
    val id: Int,
    val name: String,
    val species: Species,
)