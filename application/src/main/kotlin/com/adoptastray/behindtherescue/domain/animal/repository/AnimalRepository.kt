package com.adoptastray.behindtherescue.domain.animal.repository

import com.adoptastray.behindtherescue.domain.animal.Species
import com.adoptastray.behindtherescue.domain.animal.entity.Animal
import com.adoptastray.petango.AdoptableSearchResponse
import com.adoptastray.petango.WsAdoption
import com.adoptastray.petango.WsAdoptionSoap
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository

typealias AdoptableSearchDetails = AdoptableSearchResponse.AdoptableSearchResult.XmlNode.AdoptableSearch

private const val SITE = "Stray Animal Adoption Program"

fun toAnimal(details: AdoptableSearchDetails) = Animal(
    details.id.toInt(),
    details.name,
    Species.valueOf(details.species.uppercase()),
)

@Repository
class AnimalRepository {
    @Value("\${behind-the-rescue.petango.auth-key}")
    private lateinit var authKey: String

    private val animalClient: WsAdoptionSoap = WsAdoption().wsAdoptionSoap

    fun findById(id: Int): Animal {
        val details = animalClient.adoptableDetails(id.toString(), authKey).adoptableDetails
        return Animal(
            details.id.toInt(),
            details.animalName,
            Species.valueOf(details.species.uppercase()),
        )
    }

    private fun getAllInternal(): List<AdoptableSearchDetails> = animalClient.adoptableSearch(
        authKey,
        "",
        "",
        "",
        "",
        SITE,
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
    ).xmlNode
        .map { node -> node.adoptableSearch }
        .filterNotNull()

    fun findAll(): List<Animal> = getAllInternal()
        .map { details -> toAnimal(details) }

    fun findByIds(ids: Set<Int>): List<Animal> = getAllInternal()
        .filter { details -> ids.contains(details.id.toInt()) }
        .map { details -> toAnimal(details) }
}