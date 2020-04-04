package io.proj4.ezgas.repository

import io.proj4.ezgas.model.FuelType
import io.proj4.ezgas.model.Station
import io.proj4.ezgas.request.NearbyQuery
import io.proj4.ezgas.request.PageQuery
import io.proj4.ezgas.response.StationWithDistanceDto
import org.springframework.data.domain.Page

interface StationRepository {
    fun findById(id: Int): Station?

    fun findByIdsAndFuelType(ids: Collection<Int>, vararg fuelTypes: FuelType): List<Station>

    fun findNearby(nearbyQuery: NearbyQuery, pageQuery: PageQuery): Page<StationWithDistanceDto>?
}