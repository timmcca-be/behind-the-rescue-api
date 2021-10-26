package com.adoptastray.behindtherescue.domain.cratestacks

import com.adoptastray.behindtherescue.domain.cratereservation.entity.CrateReservation
import java.util.*

typealias CrateStack = List<Crate>

private val comparator = CrateReservationComparator()

class CrateStacks {
    val crateStacks: List<CrateStack>

    constructor(crateReservations: Collection<CrateReservation>) {
        val vaccinatedCrates = PriorityQueue(comparator)
        val nonVaccinatedCrates = PriorityQueue(comparator)
        for (crateReservation in crateReservations) {
            if (crateReservation.fullyVaccinated) {
                vaccinatedCrates.add(crateReservation)
            } else {
                nonVaccinatedCrates.add(crateReservation)
            }
        }

        var crateStacks = listOf<CrateStack>()
        while (nonVaccinatedCrates.size > 0) {
            val nonVaccinated = nonVaccinatedCrates.remove()
            val newStack: CrateStack =
                if (vaccinatedCrates.size > 0 && nonVaccinated.crateSize <= vaccinatedCrates.peek().crateSize) {
                    val vaccinated = vaccinatedCrates.remove()
                    listOf(
                        Crate(vaccinated.crateSize),
                        Crate(nonVaccinated.crateSize),
                    )
                } else {
                    listOf(
                        Crate(nonVaccinated.crateSize, isEmpty = true),
                        Crate(nonVaccinated.crateSize),
                    )
                }
            crateStacks = crateStacks.plus(listOf(newStack))
        }
        while (vaccinatedCrates.size > 0) {
            val vaccinated = vaccinatedCrates.remove()
            val newStack: CrateStack = listOf(Crate(vaccinated.crateSize))
            crateStacks = crateStacks.plus(listOf(newStack))
        }
        this.crateStacks = crateStacks
    }
}