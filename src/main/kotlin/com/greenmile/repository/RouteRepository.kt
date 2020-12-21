package com.greenmile.repository

import com.greenmile.domain.Route
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RouteRepository: CoroutineCrudRepository<Route, Int> {

    suspend fun getRouteByEquipment_Id(id: Int): Route?

}