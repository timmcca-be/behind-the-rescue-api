package com.adoptastray.behindtherescue.web.meetandgreet.message

import com.adoptastray.behindtherescue.application.meetandgreet.dto.EventMeetAndGreetDto

data class GetMeetAndGreetsByEventResponse(val meetAndGreets: List<EventMeetAndGreetDto>)
