package io.proj4.ezgas.service

import io.proj4.ezgas.model.FuelType
import io.proj4.ezgas.model.Station
import io.proj4.ezgas.request.NearbyQuery
import io.proj4.ezgas.request.PageQuery
import org.springframework.stereotype.Service

@Service
class StationService {

    fun findById(id: Int): Station {
        TODO("Not implemented")
    }

    fun findByIdsAndFuels(ids: Set<Int>, fuelTypes: Set<FuelType>?): List<Station> =
        emptyList()

    fun findNearby(nearbyQuery: NearbyQuery, pageQuery: PageQuery): List<Station> =
        emptyList()
}
