package com.adoptastray.behindtherescue.application.meetandgreet.service

import com.adoptastray.behindtherescue.application.common.DateProvider
import com.adoptastray.behindtherescue.application.meetandgreet.dto.CanceledMeetAndGreetDto
import com.adoptastray.behindtherescue.application.meetandgreet.dto.EventMeetAndGreetDto
import com.adoptastray.behindtherescue.application.meetandgreet.dto.MeetAndGreetDto
import com.adoptastray.behindtherescue.domain.adoptionevent.repository.AdoptionEventRepository
import com.adoptastray.behindtherescue.domain.animal.repository.AnimalRepository
import com.adoptastray.behindtherescue.domain.meetandgreet.repository.MeetAndGreetRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.time.LocalDate

@Service
class MeetAndGreetService (
    val adoptionEventRepository: AdoptionEventRepository,
    val meetAndGreetRepository: MeetAndGreetRepository,
    val animalRepository: AnimalRepository,
    val dateProvider: DateProvider,
) {
    @Transactional(readOnly = true)
    fun getByEvent(adoptionEventID: Int, date: LocalDate): List<EventMeetAndGreetDto> {
        val adoptionEvent = adoptionEventRepository.findById(adoptionEventID).get()
        val meetAndGreets = meetAndGreetRepository.findByAdoptionEventIdAndDateOrderByTimestampAsc(adoptionEventID, date)
        val animalsMap = animalRepository.findBySpecies(adoptionEvent.availableSpecies)
            .associateBy { it.id };
        return meetAndGreets
            .filter { it.animalID in animalsMap }
            .map { meetAndGreet -> EventMeetAndGreetDto(
                meetAndGreet,
                animalsMap[meetAndGreet.animalID]!!,
            ) }
    }

    @Transactional
    fun schedule(
        adoptionEventID: Int,
        date: LocalDate,
        timestamp: Instant,
        animalID: Int,
        potentialAdopterName: String,
        fullyVaccinated: Boolean,
    ): MeetAndGreetDto {
        val adoptionEvent = adoptionEventRepository.findById(adoptionEventID).get()
        val animal = animalRepository.findBySpeciesAndID(adoptionEvent.availableSpecies, animalID)
        require(animal != null) { "Invalid animal ID" }
        val meetAndGreet = adoptionEvent.scheduleMeetAndGreet(
            date,
            timestamp,
            animal,
            potentialAdopterName,
            fullyVaccinated,
        )
        meetAndGreetRepository.save(meetAndGreet)
        return MeetAndGreetDto(meetAndGreet, animal, dateProvider.today(adoptionEvent.timeZone))
    }

    @Transactional
    fun cancel(meetAndGreetID: Int): CanceledMeetAndGreetDto {
        val meetAndGreet = meetAndGreetRepository.findById(meetAndGreetID).get()
        meetAndGreetRepository.delete(meetAndGreet)
        return CanceledMeetAndGreetDto(meetAndGreet)
    }
}
