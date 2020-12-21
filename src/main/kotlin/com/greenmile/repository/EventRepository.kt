package com.greenmile.repository

import com.greenmile.domain.Event
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface EventRepository: ReactiveMongoRepository<Event, String>