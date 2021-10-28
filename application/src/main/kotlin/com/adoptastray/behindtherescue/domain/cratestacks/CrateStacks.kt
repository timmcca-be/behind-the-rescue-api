package com.adoptastray.behindtherescue.domain.cratestacks

import com.adoptastray.behindtherescue.domain.cratereservation.entity.CrateReservation
import java.util.PriorityQueue

class CrateStacks(crateReservations: Collection<CrateReservation>) {
    val crateStacks: List<CrateStack>

    init {
        val vaccinatedCrates = PriorityQueue<Crate>()
        val nonVaccinatedCrates = PriorityQueue<Crate>()
        for (crateReservation in crateReservations) {
            if (crateReservation.fullyVaccinated) {
                vaccinatedCrates.add(Crate(crateReservation))
            } else {
                nonVaccinatedCrates.add(Crate(crateReservation))
            }
        }

        val crateStacks = mutableListOf<CrateStack>()
        while (nonVaccinatedCrates.isNotEmpty()) {
            val crate = nonVaccinatedCrates.remove()
            crateStacks.add(
                if (crate.canStackOn(vaccinatedCrates.peek())) {
                    CrateStack(vaccinatedCrates.remove(), crate)
                } else {
                    CrateStack(crate.createEmptyCrateToStackOn(), crate)
                }
            )
        }
        while (vaccinatedCrates.isNotEmpty()) {
            crateStacks.add(CrateStack(vaccinatedCrates.remove()))
        }
        this.crateStacks = crateStacks.toList()
    }
}