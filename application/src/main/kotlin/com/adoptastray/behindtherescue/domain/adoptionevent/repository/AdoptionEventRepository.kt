package com.adoptastray.behindtherescue.domain.adoptionevent.repository

import com.adoptastray.behindtherescue.domain.adoptionevent.entity.AdoptionEvent
import org.springframework.data.repository.CrudRepository

interface AdoptionEventRepository : CrudRepository<AdoptionEvent, Int>