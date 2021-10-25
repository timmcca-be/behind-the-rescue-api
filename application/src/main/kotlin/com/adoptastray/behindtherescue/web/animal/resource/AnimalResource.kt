package com.adoptastray.behindtherescue.web.animal.resource

import com.adoptastray.behindtherescue.application.animal.service.AnimalService
import com.adoptastray.behindtherescue.web.animal.message.GetAllAnimalsResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/animals")
class AnimalResource(val service: AnimalService) {
    @GetMapping
    fun getAll(): GetAllAnimalsResponse = GetAllAnimalsResponse(service.getAll())
}