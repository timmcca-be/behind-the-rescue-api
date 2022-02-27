package com.adoptastray.behindtherescue.application.cratereservation.service

import com.adoptastray.behindtherescue.application.common.DateProvider
import com.adoptastray.behindtherescue.application.cratereservation.dto.CrateReservationDto
import com.adoptastray.behindtherescue.application.cratereservation.dto.EventCrateReservationsDto
import com.adoptastray.behindtherescue.application.cratereservation.dto.EventCrateReservationDto
import com.adoptastray.behindtherescue.domain.adoptionevent.repository.AdoptionEventRepository
import com.adoptastray.behindtherescue.domain.animal.repository.AnimalRepository
import com.adoptastray.behindtherescue.domain.cratereservation.CrateSize
import com.adoptastray.behindtherescue.domain.cratereservation.repository.CrateReservationRepository
import com.adoptastray.behindtherescue.domain.cratestacks.CrateStacks
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class CrateReservationService (
    val adoptionEventRepository: AdoptionEventRepository,
    val crateReservationRepository: CrateReservationRepository,
    val animalRepository: AnimalRepository,
    val dateProvider: DateProvider,
) {
    @Transactional(readOnly = true)
    fun getByEvent(adoptionEventID: Int, date: LocalDate): EventCrateReservationsDto {
        val adoptionEvent = adoptionEventRepository.findById(adoptionEventID).get()
        val crateReservations = crateReservationRepository.findByAdoptionEventIdAndDate(adoptionEventID, date)
        // TODO: should refactor data access to abstract away the JPA bits
        // that way the animals can be populated on the reservation by the repo
        val animalsMap = animalRepository.findBySpecies(adoptionEvent.availableSpecies)
            .associateBy { it.id };
        val crateReservationDtos = crateReservations.map { reservation -> EventCrateReservationDto(
            reservation,
            reservation.animalIDs.mapNotNull { animalsMap[it] },
        ) }
        val crateStacks = CrateStacks(crateReservations)
        return EventCrateReservationsDto(crateReservationDtos, crateStacks)
    }

    @Transactional
    fun reserve(
        adoptionEventID: Int,
        date: LocalDate,
        animalIDs: Set<Int>,
        crateSize: CrateSize,
        fullyVaccinated: Boolean,
    ): CrateReservationDto {
        val adoptionEvent = adoptionEventRepository.findById(adoptionEventID).get()
        val animals = animalRepository.findAvailableBySpecies(adoptionEvent.availableSpecies, date)
            .filter { animalIDs.contains(it.id) }
        require(animals.size == animalIDs.size) { "Invalid animal ID(s)" }
        val crateReservation = adoptionEvent.reserveCrate(date, animals, crateSize, fullyVaccinated)
        crateReservationRepository.save(crateReservation)
        return CrateReservationDto(crateReservation, animals, dateProvider.today(adoptionEvent.timeZone))
    }

    @Transactional
    fun cancel(crateReservationID: Int) = crateReservationRepository.deleteById(crateReservationID)
}