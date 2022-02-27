package com.adoptastray.behindtherescue.domain.animal

class Breed {
    val value: String
    constructor(primaryBreed: String, secondaryBreed: String?) {
        if (secondaryBreed == null || secondaryBreed.isBlank())
            value = primaryBreed
        else if (secondaryBreed.lowercase() == "mix")
            value = "$primaryBreed mix"
        else
            value = "$primaryBreed / $secondaryBreed"
    }
}