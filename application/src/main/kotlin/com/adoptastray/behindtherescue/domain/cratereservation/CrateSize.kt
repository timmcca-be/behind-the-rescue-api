package com.adoptastray.behindtherescue.domain.cratereservation

enum class CrateSize {
    SMALL, MEDIUM, LARGE, EXTRA_LARGE;

    val minimumSizeToStackOn get() = if (this === SMALL) MEDIUM else this
}