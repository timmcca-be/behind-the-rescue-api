package com.adoptastray.behindtherescue.infrastructure.animal.api

import com.adoptastray.behindtherescue.application.animal.api.AnimalAPI
import com.adoptastray.behindtherescue.application.animal.dto.AnimalDetailsDto
import com.adoptastray.behindtherescue.domain.animal.Breed
import com.adoptastray.behindtherescue.domain.animal.Sex
import com.adoptastray.behindtherescue.domain.animal.Species
import com.adoptastray.behindtherescue.domain.animal.entity.Animal
import com.adoptastray.petango.AdoptableSearchResponse
import com.adoptastray.petango.WsAdoption
import com.adoptastray.petango.WsAdoptionSoap
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import java.time.LocalDate

// private val compressedPhotoPattern = Regex("_TN1\\.jpg$")

private fun parseAnimal(xmlNode: AdoptableSearchResponse.AdoptableSearchResult.XmlNode): Animal? {
    if (xmlNode.adoptableSearch == null) {
        return null
    }
    val details = xmlNode.adoptableSearch
    return Animal(
        details.id.toInt(),
        details.name,
        Species.valueOf(details.species.uppercase()),
        // compressedPhotoPattern.replace(it.photo, ".jpg"),
        details.photo,
        details.sublocation,
        Breed(details.primaryBreed, details.secondaryBreed),
    )
}

@Component
class PetangoAnimalAPI : AnimalAPI {
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
    ).xmlNode.mapNotNull { parseAnimal(it) }

    override fun getAnimalDetails(animalID: Int): AnimalDetailsDto {
        val details = animalClient.adoptableDetails(animalID.toString(), authKey).adoptableDetails
        val breed = Breed(details.primaryBreed, details.secondaryBreed)

        return AnimalDetailsDto(
            details.id.toInt(),
            details.animalName,
            Species.valueOf(details.species.uppercase()),
            Sex.valueOf(details.sex.uppercase()),
            details.sublocation,
            listOfNotNull(details.photo1, details.photo2, details.photo3),
            LocalDate.parse(details.dateOfBirth),
            details.age.toInt(),
            breed.value,
            details.dsc,
        )
    }
}
