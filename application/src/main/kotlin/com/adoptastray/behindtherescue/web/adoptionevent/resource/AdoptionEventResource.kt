package com.adoptastray.behindtherescue.web.adoptionevent.resource

import com.adoptastray.behindtherescue.application.adoptionevent.service.AdoptionEventService
import com.adoptastray.behindtherescue.web.adoptionevent.message.CreateAdoptionEventRequest
import com.adoptastray.behindtherescue.web.adoptionevent.message.CreateAdoptionEventResponse
import com.adoptastray.behindtherescue.web.adoptionevent.message.GetAdoptionEventResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/adoption-events")
class AdoptionEventResource(val adoptionEventService: AdoptionEventService) {
    @GetMapping("/{adoptionEventID}")
    fun get(@PathVariable adoptionEventID: Int): GetAdoptionEventResponse {
        return GetAdoptionEventResponse(adoptionEventService.get(adoptionEventID));
    }

    @PostMapping
    fun create(@RequestBody request: CreateAdoptionEventRequest): CreateAdoptionEventResponse {
        return CreateAdoptionEventResponse(adoptionEventService.create(
            request.name,
            request.availableSpecies,
            request.dayOfWeek,
        ))
    }
}
