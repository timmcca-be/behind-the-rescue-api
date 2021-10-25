package com.adoptastray.behindtherescue.application.cratereservation.service

import com.adoptastray.behindtherescue.application.common.DateProvider
import com.adoptastray.behindtherescue.application.cratereservation.dto.CrateReservationDto
import com.adoptastray.behindtherescue.application.cratereservation.dto.ListCrateReservationDto
import com.adoptastray.behindtherescue.domain.adoptionevent.repository.AdoptionEventRepository
import com.adoptastray.behindtherescue.domain.animal.repository.AnimalRepository
import com.adoptastray.behindtherescue.domain.cratereservation.CrateSize
import com.adoptastray.behindtherescue.domain.cratereservation.repository.CrateReservationRepository
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
    fun getByEvent(adoptionEventID: Int, date: LocalDate): List<ListCrateReservationDto> {
        val adoptionEvent = adoptionEventRepository.findById(adoptionEventID)
        require(adoptionEvent.isPresent) { "No adoption event with ID $adoptionEventID" }
        val crateReservations = crateReservationRepository.findByAdoptionEventIdAndDate(adoptionEventID, date)
        val animalsMap = animalRepository.findAll().associateBy { it.id };
        return crateReservations.map { crateReservation -> ListCrateReservationDto(
            crateReservation,
            crateReservation.animalIDs.map { animalID -> animalsMap[animalID] }.filterNotNull(),
        ) }
    }

    @Transactional
    fun reserve(
        adoptionEventID: Int,
        date: LocalDate,
        animalIDs: Set<Int>,
        crateSize: CrateSize,
        fullyVaccinated: Boolean,
    ): CrateReservationDto {
        val adoptionEvent = adoptionEventRepository.findById(adoptionEventID)
        require(adoptionEvent.isPresent) { "No adoption event with ID $adoptionEventID" }
        val animals = animalRepository.findAll().filter { animal -> animalIDs.contains(animal.id) }
        val crateReservation = adoptionEvent.get().reserveCrate(date, animals, crateSize, fullyVaccinated)
        crateReservationRepository.save(crateReservation)
        return CrateReservationDto(crateReservation, animals, dateProvider.today)
    }
}