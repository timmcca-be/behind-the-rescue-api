package com.adoptastray.behindtherescue.domain.animal.repository

import com.adoptastray.behindtherescue.domain.animal.Species
import com.adoptastray.behindtherescue.domain.animal.entity.Animal
import com.adoptastray.petango.AdoptableSearchResponse
import com.adoptastray.petango.WsAdoption
import com.adoptastray.petango.WsAdoptionSoap
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository

typealias AdoptableSearchDetails = AdoptableSearchResponse.AdoptableSearchResult.XmlNode.AdoptableSearch

fun toAnimal(details: AdoptableSearchDetails) = Animal(
    details.id.toInt(),
    details.name,
    Species.valueOf(details.species.uppercase()),
)

@Repository
class AnimalRepository {
    @Value("\${behind-the-rescue.petango.auth-key}")
    private lateinit var authKey: String
    @Value("\${behind-the-rescue.petango.site}")
    private lateinit var site: String

    private val animalClient: WsAdoptionSoap = WsAdoption().wsAdoptionSoap

    fun findById(id: Int): Animal {
        val details = animalClient.adoptableDetails(id.toString(), authKey).adoptableDetails
        return Animal(
            details.id.toInt(),
            details.animalName,
            Species.valueOf(details.species.uppercase()),
        )
    }

    @Cacheable(value = ["animals"], key="'all'")
    fun findAll(): List<Animal> = animalClient.adoptableSearch(
        authKey, "", "", "", "",
        site, "", "", "", "", "", "", "", "", "",
    ).xmlNode
        .map { node -> node.adoptableSearch }
        .filterNotNull()
        .map { details -> Animal(
            details.id.toInt(),
            details.name,
            Species.valueOf(details.species.uppercase()),
        ) }
}