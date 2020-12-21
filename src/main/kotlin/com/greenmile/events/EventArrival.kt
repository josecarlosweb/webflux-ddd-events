package com.greenmile.events

import com.greenmile.domain.Coordinate
import com.greenmile.domain.Event
import com.greenmile.domain.Route
import com.greenmile.domain.Stop
import com.greenmile.domain.enum.EventType
import com.greenmile.repository.EventRepository
import com.greenmile.repository.RouteRepository
import com.greenmile.util.haversineDistance
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import java.util.*

@Component
class EventArrival(
        private val routeRepository: RouteRepository,
        private val eventRepository: EventRepository
) : IEventsProcessor {

    private val log = LoggerFactory.getLogger(EventArrival::class.java)

    /**
     * para detectar se o motorista chegou em algum cliente precisamos saber se
     * 1. esta dentro do raio de atendimento e
     * 2. se essa e a coordenada anterior são iguais
     */
    override suspend fun processCoordinate(notificationDto: NotificationDto) {
        val lastCoordinate = notificationDto.lastCoordinate
        val coordinate = notificationDto.coordinate
        if (lastCoordinate.latitude == coordinate.latitude && lastCoordinate.longitude == coordinate.longitude) {
            val route = routeRepository.getRouteByEquipment_Id(notificationDto.coordinate.equipmentId)
            if(route != null){
                filterListStops(coordinate, route.stops).forEach { stop ->
                    addArrivedStopOnRoute(route, stop)
                    registerEvent(EventType.ARRIVE, stop.id)
                }
            }
        }
    }

    private suspend fun registerEvent(eventType: EventType, stopId: Int): Event {
        val newEvent = Event(eventType = eventType, `when` = Date(), stopId = stopId)
        log.info("A new Arrive event launch {}", newEvent)
        return eventRepository.save(newEvent)
    }

    private suspend  fun addArrivedStopOnRoute(route: Route, oldStop: Stop): Route {
        val updatedStop = oldStop.copy(arrivalAt = Date())
        val locationOfStop = route.stops.indexOf(oldStop)
        route.stops.removeAt(locationOfStop)
        route.stops.add(updatedStop)
        return routeRepository.save(route)
    }

    private suspend fun filterListStops(coordinate: Coordinate, stops: List<Stop>) = stops.filter { stop ->
        val distance = haversineDistance(stop.latitude, stop.longitude, coordinate.latitude, coordinate.longitude)
        stop.arrivalAt == null && distance <= geofence
    }

}