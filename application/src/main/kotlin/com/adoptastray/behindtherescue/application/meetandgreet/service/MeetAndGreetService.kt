package com.adoptastray.behindtherescue.application.meetandgreet.service

import com.adoptastray.behindtherescue.application.common.DateProvider
import com.adoptastray.behindtherescue.application.meetandgreet.dto.EventMeetAndGreetDto
import com.adoptastray.behindtherescue.application.meetandgreet.dto.MeetAndGreetDto
import com.adoptastray.behindtherescue.domain.adoptionevent.repository.AdoptionEventRepository
import com.adoptastray.behindtherescue.domain.animal.repository.AnimalRepository
import com.adoptastray.behindtherescue.domain.meetandgreet.repository.MeetAndGreetRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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
        val meetAndGreets = meetAndGreetRepository.findByAdoptionEventIdAndDate(adoptionEventID, date)
        val animalsMap = animalRepository.findBySpecies(adoptionEvent.availableSpecies)
            .associateBy { it.id };
        return meetAndGreets.map { meetAndGreet -> EventMeetAndGreetDto(
            meetAndGreet,
            animalsMap[meetAndGreet.animalID],
        ) }
    }

    @Transactional
    fun schedule(
        adoptionEventID: Int,
        date: LocalDate,
        animalID: Int,
        fullyVaccinated: Boolean,
    ): MeetAndGreetDto {
        val adoptionEvent = adoptionEventRepository.findById(adoptionEventID).get()
        val animal = animalRepository.findAvailableByID(animalID, date)
        require(animal != null) { "Invalid animal ID" }
        val meetAndGreet = adoptionEvent.scheduleMeetAndGreet(date, animal, fullyVaccinated)
        meetAndGreetRepository.save(meetAndGreet)
        return MeetAndGreetDto(meetAndGreet, animal, dateProvider.today)
    }

    @Transactional
    fun cancel(meetAndGreetID: Int) = meetAndGreetRepository.deleteById(meetAndGreetID)
}
