package com.adoptastray.behindtherescue.application.cratereservation.dto

import com.adoptastray.behindtherescue.domain.cratereservation.CrateSize
import com.adoptastray.behindtherescue.domain.cratestacks.Crate

data class CrateDto(val size: CrateSize, val isEmpty: Boolean) {
    constructor(crate: Crate) : this(crate.size, crate.isEmpty)
}