package com.adoptastray.behindtherescue.application.adoptionevent.service

import com.adoptastray.behindtherescue.application.adoptionevent.dto.AdoptionEventDto
import com.adoptastray.behindtherescue.domain.adoptionevent.entity.AdoptionEvent
import com.adoptastray.behindtherescue.domain.adoptionevent.repository.AdoptionEventRepository
import com.adoptastray.behindtherescue.domain.animal.Species
import org.springframework.stereotype.Service
import java.time.DayOfWeek

@Service
class AdoptionEventService(val adoptionEventRepository: AdoptionEventRepository) {
    fun getAll(): List<AdoptionEventDto> {
        return adoptionEventRepository.findAll().map { adoptionEvent -> AdoptionEventDto(adoptionEvent) }
    }

    fun get(adoptionEventID: Int): AdoptionEventDto {
        val adoptionEvent = adoptionEventRepository.findById(adoptionEventID)
        require(adoptionEvent.isPresent) { "No adoption event with ID $adoptionEventID" }
        return AdoptionEventDto(adoptionEvent.get())
    }

    fun create(name: String, availableSpecies: Species, dayOfWeek: DayOfWeek): AdoptionEventDto {
        val adoptionEvent = AdoptionEvent(
            name = name,
            availableSpecies = availableSpecies,
            dayOfWeek = dayOfWeek,
        )
        adoptionEventRepository.save(adoptionEvent)
        return AdoptionEventDto(adoptionEvent)
    }
}