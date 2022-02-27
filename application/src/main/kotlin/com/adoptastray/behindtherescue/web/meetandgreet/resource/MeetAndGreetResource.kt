package com.adoptastray.behindtherescue.web.meetandgreet.resource

import com.adoptastray.behindtherescue.application.meetandgreet.service.MeetAndGreetService
import com.adoptastray.behindtherescue.web.meetandgreet.message.CancelMeetAndGreetResponse
import com.adoptastray.behindtherescue.web.meetandgreet.message.GetMeetAndGreetsByEventResponse
import com.adoptastray.behindtherescue.web.meetandgreet.message.ScheduleMeetAndGreetRequest
import com.adoptastray.behindtherescue.web.meetandgreet.message.ScheduleMeetAndGreetResponse
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
class MeetAndGreetResource(val service: MeetAndGreetService) {
    @GetMapping("/adoption-events/{adoptionEventID}/dates/{date}/meet-and-greets")
    fun get(
        @PathVariable adoptionEventID: Int,
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate,
    ): GetMeetAndGreetsByEventResponse =
        GetMeetAndGreetsByEventResponse(service.getByEvent(adoptionEventID, date))

    @PostMapping("/adoption-events/{adoptionEventID}/dates/{date}/meet-and-greets")
    fun schedule(
        @PathVariable adoptionEventID: Int,
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate,
        @RequestBody request: ScheduleMeetAndGreetRequest,
    ): ScheduleMeetAndGreetResponse = ScheduleMeetAndGreetResponse(service.schedule(
        adoptionEventID,
        date,
        request.timestamp,
        request.animalID,
        request.potentialAdopterName,
        request.fullyVaccinated,
    ))

    @DeleteMapping("/meet-and-greets/{meetAndGreetID}")
    fun cancel(
        @PathVariable meetAndGreetID: Int,
    ) = CancelMeetAndGreetResponse(service.cancel(meetAndGreetID))
}
