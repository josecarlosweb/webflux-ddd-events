package com.greenmile.domain

import com.greenmile.domain.enum.EventType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
@Document
data class Event(
        @Id val _id: String? = null,
        val eventType: EventType,
        val `when`: Date = Date(),
        val stopId: Int
)