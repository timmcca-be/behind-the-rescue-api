package com.adoptastray.behindtherescue.domain.animal.repository

import com.adoptastray.behindtherescue.domain.animal.Species
import com.adoptastray.behindtherescue.domain.animal.entity.Animal
import com.adoptastray.petango.AdoptableSearchResponse
import com.adoptastray.petango.WsAdoption
import com.adoptastray.petango.WsAdoptionSoap
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

// private val compressedPhotoPattern = Regex("_TN1\\.jpg$")

@Component
class AnimalAPI {
    @Value("\${behind-the-rescue.petango.auth-key}")
    private lateinit var authKey: String
    @Value("\${behind-the-rescue.petango.site}")
    private lateinit var site: String

    private val animalClient: WsAdoptionSoap = WsAdoption().wsAdoptionSoap

    @Cacheable(value = ["animals"])
    fun findBySpecies(species: Species): List<Animal> = animalClient.adoptableSearch(
        authKey,
        if (species == Species.DOG) "1" else "2",
        "", "", "",
        site,
        "", "", "", "", "", "", "", "", "",
    ).xmlNode
        .map { it.adoptableSearch }
        .filterNotNull()
        .map { Animal(
            it.id.toInt(),
            it.name,
            Species.valueOf(it.species.uppercase()),
            // compressedPhotoPattern.replace(it.photo, ".jpg"),
            it.photo,
            it.sublocation
        ) }
}