package io.proj4.ezgas.repository

import io.proj4.ezgas.model.FuelType
import io.proj4.ezgas.model.Station
import io.proj4.ezgas.model.StationIdWithDistance
import io.proj4.ezgas.request.NearbyRequest

interface StationRepository {
    fun findById(id: Int): Station?
    fun findByIds(ids: Collection<Int>, vararg fuelTypes: FuelType): List<Station>
    fun findIdsWithDistance(nearbyRequest: NearbyRequest): List<StationIdWithDistance>
}