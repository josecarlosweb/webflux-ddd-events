package com.greenmile.repository

import com.greenmile.domain.LastCoordinate
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LastCoordinateRepository: CoroutineCrudRepository<LastCoordinate, String> {

    suspend fun getLastCoordinateByEquipment_Id(id: Int): LastCoordinate?

}