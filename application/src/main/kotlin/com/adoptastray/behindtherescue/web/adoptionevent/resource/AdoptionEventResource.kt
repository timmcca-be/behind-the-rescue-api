package com.adoptastray.behindtherescue.web.adoptionevent.resource

import com.adoptastray.behindtherescue.application.adoptionevent.service.AdoptionEventService
import com.adoptastray.behindtherescue.web.adoptionevent.message.CreateAdoptionEventRequest
import com.adoptastray.behindtherescue.web.adoptionevent.message.CreateAdoptionEventResponse
import com.adoptastray.behindtherescue.web.adoptionevent.message.GetAdoptionEventResponse
import com.adoptastray.behindtherescue.web.adoptionevent.message.GetAllAdoptionEventsResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/adoption-events")
class AdoptionEventResource(val service: AdoptionEventService) {
    @GetMapping
    fun getAll(): GetAllAdoptionEventsResponse = GetAllAdoptionEventsResponse(service.getAll());

    @GetMapping("/{adoptionEventID}")
    fun get(@PathVariable adoptionEventID: Int): GetAdoptionEventResponse {
        return GetAdoptionEventResponse(service.get(adoptionEventID));
    }

    @PostMapping
    fun create(@RequestBody request: CreateAdoptionEventRequest): CreateAdoptionEventResponse =
        CreateAdoptionEventResponse(service.create(
            request.name,
            request.location,
            request.availableSpecies,
            request.dayOfWeek,
        ))
}
