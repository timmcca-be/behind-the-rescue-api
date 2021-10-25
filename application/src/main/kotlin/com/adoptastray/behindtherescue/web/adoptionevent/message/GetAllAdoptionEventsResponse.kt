package com.adoptastray.behindtherescue.web.adoptionevent.message

import com.adoptastray.behindtherescue.application.adoptionevent.dto.AdoptionEventDto

data class GetAllAdoptionEventsResponse(val adoptionEvents: List<AdoptionEventDto>)