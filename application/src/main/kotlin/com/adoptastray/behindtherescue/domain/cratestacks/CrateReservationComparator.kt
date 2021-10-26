package com.adoptastray.behindtherescue.domain.cratestacks

import com.adoptastray.behindtherescue.domain.cratereservation.entity.CrateReservation

class CrateReservationComparator : Comparator<CrateReservation> {
    override fun compare(a: CrateReservation, b: CrateReservation): Int = -a.crateSize.compareTo(b.crateSize)
}