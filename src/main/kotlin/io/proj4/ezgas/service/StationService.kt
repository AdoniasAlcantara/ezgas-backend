package io.proj4.ezgas.service

import io.proj4.ezgas.error.ResourceNotFoundException
import io.proj4.ezgas.model.FuelType
import io.proj4.ezgas.model.Station
import io.proj4.ezgas.repository.StationRepository
import io.proj4.ezgas.request.NearbyQuery
import io.proj4.ezgas.request.PageQuery
import io.proj4.ezgas.response.mappers.toDto
import org.springframework.stereotype.Service

@Service
class StationService(private val repository: StationRepository) {

    fun findById(id: Int) = repository
            .findById(id)
            ?.toDto()
            ?: throw ResourceNotFoundException("No station found with id: $id")

    fun findByIds(ids: Set<Int>, fuelTypes: Array<FuelType>) = repository
            .findByIdsAndFuelType(ids, *fuelTypes)
            .ifEmpty { throw ResourceNotFoundException("No stations found with ids: $ids") }
            .map(Station::toDto)

    fun findNearby(nearbyQuery: NearbyQuery, pageQuery: PageQuery) = repository
            .findNearby(nearbyQuery, pageQuery)
            ?: throw ResourceNotFoundException("No stations found at current location")
}