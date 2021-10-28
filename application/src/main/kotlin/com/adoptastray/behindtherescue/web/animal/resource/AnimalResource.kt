package com.adoptastray.behindtherescue.web.animal.resource

import com.adoptastray.behindtherescue.application.animal.service.AnimalService
import com.adoptastray.behindtherescue.domain.animal.Species
import com.adoptastray.behindtherescue.web.animal.message.GetAnimalsResponse
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/available-animals/{date}")
class AnimalResource(val service: AnimalService) {
    @GetMapping("/cats")
    fun getAvailableCats(
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate,
    ): GetAnimalsResponse = GetAnimalsResponse(service.getAvailableAnimals(Species.CAT, date))

    @GetMapping("/dogs")
    fun getAvailableDogs(
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate,
    ): GetAnimalsResponse = GetAnimalsResponse(service.getAvailableAnimals(Species.DOG, date))
}