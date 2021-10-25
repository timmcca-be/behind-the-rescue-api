package com.adoptastray.behindtherescue.application.cratereservation.service

import com.adoptastray.behindtherescue.application.cratereservation.dto.CrateReservationDto
import com.adoptastray.behindtherescue.application.cratereservation.dto.ListCrateReservationDto
import com.adoptastray.behindtherescue.domain.adoptionevent.repository.AdoptionEventRepository
import com.adoptastray.behindtherescue.domain.animal.repository.AnimalRepository
import com.adoptastray.behindtherescue.domain.cratereservation.CrateSize
import com.adoptastray.behindtherescue.domain.cratereservation.repository.CrateReservationRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CrateReservationService (
    val adoptionEventRepository: AdoptionEventRepository,
    val crateReservationRepository: CrateReservationRepository,
    val animalRepository: AnimalRepository,
) {
    fun getByEvent(adoptionEventID: Int, date: LocalDate): List<ListCrateReservationDto> {
        val adoptionEvent = adoptionEventRepository.findById(adoptionEventID)
        require(adoptionEvent.isPresent) { "No adoption event with ID $adoptionEventID" }
        val crateReservations = crateReservationRepository.findByAdoptionEventIdAndDate(adoptionEventID, date)
        val animalIDs = crateReservations
            .fold(setOf<Int>()) { acc, value -> acc.plus(value.animalIDs)}
            .toSet()
        val animals = animalRepository.findByIds(animalIDs)
        val animalMap = animals.associateBy { it.id }
        return crateReservations.map { crateReservation -> ListCrateReservationDto(
            crateReservation,
            crateReservation.animalIDs.mapNotNull { animalID -> animalMap[animalID] },
        ) }
    }

    fun reserve(
        adoptionEventID: Int,
        date: LocalDate,
        animalIDs: Set<Int>,
        crateSize: CrateSize,
        fullyVaccinated: Boolean,
    ): CrateReservationDto {
        val adoptionEvent = adoptionEventRepository.findById(adoptionEventID)
        require(adoptionEvent.isPresent) { "No adoption event with ID $adoptionEventID" }
        val animals = animalRepository.findByIds(animalIDs)
        val crateReservation = adoptionEvent.get().reserveCrate(date, animals, crateSize, fullyVaccinated)
        crateReservationRepository.save(crateReservation)
        return CrateReservationDto(crateReservation, animals)
    }
}