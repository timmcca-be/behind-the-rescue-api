package com.adoptastray.behindtherescue.domain.animal.repository

import com.adoptastray.behindtherescue.domain.animal.Species
import com.adoptastray.behindtherescue.domain.animal.entity.Animal
import com.adoptastray.petango.AdoptableSearchResponse
import com.adoptastray.petango.WsAdoption
import com.adoptastray.petango.WsAdoptionSoap
import org.springframework.stereotype.Repository

typealias AdoptableSearchDetails = AdoptableSearchResponse.AdoptableSearchResult.XmlNode.AdoptableSearch

private const val AUTH_KEY = "TODO: pull from application.properties"
private const val SITE = "Stray Animal Adoption Program"

fun toAnimal(details: AdoptableSearchDetails) = Animal(
    details.id.toInt(),
    details.name,
    Species.valueOf(details.species.uppercase()),
)

@Repository
class AnimalRepository {
    private val animalClient: WsAdoptionSoap = WsAdoption().wsAdoptionSoap

    fun findById(id: Int): Animal {
        val details = animalClient.adoptableDetails(id.toString(), AUTH_KEY).adoptableDetails
        return Animal(
            details.id.toInt(),
            details.animalName,
            Species.valueOf(details.species.uppercase()),
        )
    }

    private fun getAllInternal(): List<AdoptableSearchDetails> = animalClient.adoptableSearch(
        AUTH_KEY,
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