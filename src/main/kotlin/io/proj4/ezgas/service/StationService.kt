package io.proj4.ezgas.service

import io.proj4.ezgas.error.ResourceNotFoundException
import io.proj4.ezgas.model.SortCriteria.DISTANCE
import io.proj4.ezgas.model.SortCriteria.PRICE
import io.proj4.ezgas.model.Station
import io.proj4.ezgas.repository.StationRepository
import io.proj4.ezgas.request.NearbyQuery
import io.proj4.ezgas.request.Paging
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class StationService(private val repository: StationRepository) {

    fun findById(id: String): Station =
        repository.findByIdOrNull(id) ?: throw ResourceNotFoundException(id)

    fun findAllById(ids: Set<String>): List<Station> =
        repository.findAllById(ids).toList()

    fun findNearby(query: NearbyQuery, paging: Paging): Page<Station> {
        val sort = when (query.sort!!) {
            DISTANCE -> Sort.by("place.distance")
            PRICE -> Sort.by("fuels.${query.fuel}.price", "place.distance")
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
