package io.proj4.ezgas.repository

import io.proj4.ezgas.model.Station
import io.proj4.ezgas.request.NearbyStationsQuery
import io.proj4.ezgas.request.StationsByIdQuery

interface StationRepository {
    fun findById(id: Int): Station?
    fun findByIds(requestQuery: StationsByIdQuery): List<Station>
    fun findNearby(requestQuery: NearbyStationsQuery): List<Station>
}