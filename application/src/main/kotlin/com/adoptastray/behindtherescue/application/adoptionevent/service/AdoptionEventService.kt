package com.adoptastray.behindtherescue.application.adoptionevent.service

import com.adoptastray.behindtherescue.application.adoptionevent.dto.AdoptionEventDto
import com.adoptastray.behindtherescue.application.common.DateProvider
import com.adoptastray.behindtherescue.domain.adoptionevent.entity.AdoptionEvent
import com.adoptastray.behindtherescue.domain.adoptionevent.repository.AdoptionEventRepository
import com.adoptastray.behindtherescue.domain.animal.Species
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId

@Service
class AdoptionEventService(
    val adoptionEventRepository: AdoptionEventRepository,
    val dateProvider: DateProvider,
) {
    @Transactional(readOnly = true)
    fun getAll(): List<AdoptionEventDto> {
        return adoptionEventRepository.findAll()
            .map { AdoptionEventDto(it, dateProvider.today) }
    }

    @Transactional(readOnly = true)
    fun get(adoptionEventID: Int): AdoptionEventDto {
        val adoptionEvent = adoptionEventRepository.findById(adoptionEventID)
        require(adoptionEvent.isPresent) { "No adoption event with ID $adoptionEventID" }
        return AdoptionEventDto(adoptionEvent.get(), dateProvider.today)
    }

    @Transactional
    fun create(
        name: String,
        location: String,
        availableSpecies: Species,
        dayOfWeek: DayOfWeek,
    ): AdoptionEventDto {
        val adoptionEvent = AdoptionEvent(
            name = name,
            location = location,
            availableSpecies = availableSpecies,
            dayOfWeek = dayOfWeek,
        )
        adoptionEventRepository.save(adoptionEvent)
        return AdoptionEventDto(adoptionEvent, dateProvider.today)
    }
}