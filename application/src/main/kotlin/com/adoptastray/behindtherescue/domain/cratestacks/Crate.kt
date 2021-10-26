package com.adoptastray.behindtherescue.domain.cratestacks

import com.adoptastray.behindtherescue.domain.cratereservation.CrateSize
import com.adoptastray.behindtherescue.domain.cratereservation.entity.CrateReservation

data class Crate(val size: CrateSize, val isEmpty: Boolean = false) : Comparable<Crate> {
    constructor(reservation: CrateReservation) : this(reservation.crateSize)

    override fun compareTo(other: Crate): Int {
        val sizeCompare = -size.compareTo(other.size)
        return if (sizeCompare != 0) {
            sizeCompare
        } else if (other.isEmpty == isEmpty) {
            0
        } else if (isEmpty) {
            1
        } else {
            -1
        }
    }

    fun canStackOn(other: Crate?): Boolean = other != null && other.size >= size.minimumSizeToStackOn
    fun createEmptyCrateToStackOn(): Crate = Crate(size.minimumSizeToStackOn, true)
}
