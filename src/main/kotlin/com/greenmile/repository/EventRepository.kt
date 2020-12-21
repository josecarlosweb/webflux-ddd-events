package com.greenmile.repository

import com.greenmile.domain.Event
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EventRepository: CoroutineCrudRepository<Event, String>