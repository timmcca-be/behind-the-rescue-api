package com.adoptastray.behindtherescue.web.animal.resource

import com.adoptastray.behindtherescue.application.animal.service.AnimalService
import com.adoptastray.behindtherescue.domain.animal.Species
import com.adoptastray.behindtherescue.web.animal.message.GetAnimalDetailsResponse
import com.adoptastray.behindtherescue.web.animal.message.GetAnimalsResponse
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/animals")
class AnimalResource(val service: AnimalService) {
    @GetMapping
    fun getAllAnimals(): GetAnimalsResponse = GetAnimalsResponse(service.getAllAnimals())

    @GetMapping("/available/{date}/cats")
    fun getAvailableCats(
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate,
    ): GetAnimalsResponse = GetAnimalsResponse(service.getAvailableAnimals(Species.CAT, date))

    @GetMapping("/available/{date}/dogs")
    fun getAvailableDogs(
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate,
    ): GetAnimalsResponse = GetAnimalsResponse(service.getAvailableAnimals(Species.DOG, date))

    @GetMapping("/{animalID}")
    fun getAnimalDetails(@PathVariable animalID: Int): GetAnimalDetailsResponse =
        GetAnimalDetailsResponse(service.getAnimalDetails(animalID))
}