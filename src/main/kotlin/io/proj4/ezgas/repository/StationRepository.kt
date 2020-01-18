package io.proj4.ezgas.repository

import io.proj4.ezgas.model.Station
import io.proj4.ezgas.request.NearbyStationsQuery
import io.proj4.ezgas.request.StationsByIdQuery

interface StationRepository {
    fun findNearby(requestQuery: NearbyStationsQuery): List<Station>
    fun findById(requestQuery: StationsByIdQuery): List<Station>
}