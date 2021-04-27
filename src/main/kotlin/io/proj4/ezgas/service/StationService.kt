package io.proj4.ezgas.service

import io.proj4.ezgas.model.FuelType
import io.proj4.ezgas.model.SortCriteria.DISTANCE
import io.proj4.ezgas.model.SortCriteria.PRICE
import io.proj4.ezgas.model.Station
import io.proj4.ezgas.repository.StationRepository
import io.proj4.ezgas.request.NearbyQuery
import io.proj4.ezgas.request.Paging
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.stereotype.Service

@Service
class StationService(private val repository: StationRepository) {

    fun findById(id: Int): Station {
        TODO("Not implemented")
    }

    fun findByIdsAndFuels(ids: Set<Int>, fuelTypes: Set<FuelType>?): List<Station> =
        emptyList()

    fun findNearby(query: NearbyQuery, paging: Paging): Slice<Station> {
        val sort = when (query.sort!!) {
            DISTANCE -> Sort.by("distance")
            PRICE -> Sort.by("fuels.${query.fuel}.price", "distance")
        }

        val pageable = PageRequest.of(
            paging.pageNumber!!,
            paging.pageSize!!,
            sort.ascending()
        )

        return repository.findNearby(
            GeoJsonPoint(query.longitude!!, query.latitude!!),
            query.distance!!,
            query.fuel!!,
            pageable
        )
    }
}
