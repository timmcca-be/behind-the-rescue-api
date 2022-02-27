package com.adoptastray.behindtherescue.domain.meetandgreet.entity

import com.adoptastray.behindtherescue.domain.adoptionevent.entity.AdoptionEvent
import java.sql.Timestamp
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity(name = "MeetAndGreet")
@Table(name = "meet_and_greets")
data class MeetAndGreet(
    @Id @GeneratedValue val id: Int = 0,
    @ManyToOne val adoptionEvent: AdoptionEvent,
    @NotNull val date: LocalDate,
    @NotNull val timestamp: Timestamp,
    @NotNull val animalID: Int,
    @NotNull val potentialAdopterName: String,
    val fullyVaccinated: Boolean,
)
