package com.adoptastray.behindtherescue.domain.meetandgreet.entity

import com.adoptastray.behindtherescue.domain.adoptionevent.entity.AdoptionEvent
import com.adoptastray.behindtherescue.domain.cratereservation.CrateSize
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity(name = "MeetAndGreet")
@Table(name = "meet_and_greets")
data class MeetAndGreet(
    @Id @GeneratedValue val id: Int = 0,
    @ManyToOne val adoptionEvent: AdoptionEvent,
    @NotNull val date: LocalDate,
    @NotNull val animalID: Int,
    val fullyVaccinated: Boolean,
)
