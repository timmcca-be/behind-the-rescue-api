package com.adoptastray.behindtherescue.web.animal.message

import com.adoptastray.behindtherescue.application.animal.dto.AnimalDto

data class GetAnimalsResponse(val animals: List<AnimalDto>)