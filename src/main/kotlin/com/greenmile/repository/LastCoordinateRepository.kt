package com.greenmile.repository

import com.greenmile.domain.LastCoordinate
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface LastCoordinateRepository: ReactiveMongoRepository<LastCoordinate, String> {

    fun getLastCoordinateByEquipment_Id(id: Int): Mono<LastCoordinate>

}