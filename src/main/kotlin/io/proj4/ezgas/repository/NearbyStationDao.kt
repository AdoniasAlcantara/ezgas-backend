package io.proj4.ezgas.repository

import io.proj4.ezgas.model.FuelType
import io.proj4.ezgas.model.Station
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.geo.Point

interface NearbyStationDao {

    fun findNearby(
        point: Point,
        distance: Double,
        fuelType: FuelType,
        pageable: Pageable
    ): Page<Station>
}
