package com.adoptastray.behindtherescue.web.cratereservation.message

import com.adoptastray.behindtherescue.domain.cratereservation.CrateSize

data class ReserveCrateRequest(val animalIDs: Set<Int>, val crateSize: CrateSize, val fullyVaccinated: Boolean)