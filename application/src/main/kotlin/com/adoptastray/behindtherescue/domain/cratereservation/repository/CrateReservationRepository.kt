package com.adoptastray.behindtherescue.domain.cratereservation.repository

import com.adoptastray.behindtherescue.domain.cratereservation.entity.CrateReservation
import org.springframework.data.repository.CrudRepository

interface CrateReservationRepository : CrudRepository<CrateReservation, Int>