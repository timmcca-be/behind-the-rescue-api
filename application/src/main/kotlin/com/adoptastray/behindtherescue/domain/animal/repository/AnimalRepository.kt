package com.adoptastray.behindtherescue.domain.animal.repository

import com.adoptastray.petango.WsAdoption
import com.adoptastray.petango.WsAdoptionSoap
import com.sun.org.apache.xerces.internal.dom.ElementNSImpl
import org.springframework.stereotype.Repository

private val AUTH_KEY = "TODO: pull from application.properties"

@Repository
class AnimalRepository {
    private val animalClient: WsAdoptionSoap = WsAdoption().getWsAdoptionSoap()

    fun findById(id: Int) {
        val result = animalClient.adoptableDetails(id.toString(), AUTH_KEY)
        // can't use because this is an internal class.
        // probably easiest approach is to fix the wsdl :/
        val element = result.content.get(0) as ElementNSImpl
    }
}