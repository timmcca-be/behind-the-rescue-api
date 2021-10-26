package com.adoptastray.behindtherescue.domain.cratestacks

import com.adoptastray.behindtherescue.domain.cratereservation.CrateSize

data class Crate(val size: CrateSize, val isEmpty: Boolean = false)
