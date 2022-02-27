package com.adoptastray.behindtherescue.domain.animal.entity

import com.adoptastray.behindtherescue.domain.animal.Breed
import com.adoptastray.behindtherescue.domain.animal.Species
import com.adoptastray.petango.AdoptableDetailsResponse
import java.io.Serializable

data class Animal(
    val id: Int,
    val name: String,
    val species: Species,
    val photoLink: String,
    val foster: String,
    val breed: Breed,
) : Serializable