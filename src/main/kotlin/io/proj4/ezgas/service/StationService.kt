package io.proj4.ezgas.service

import io.proj4.ezgas.error.ResourceNotFoundException
import io.proj4.ezgas.model.FuelType
import io.proj4.ezgas.model.Station
import io.proj4.ezgas.repository.StationRepository
import io.proj4.ezgas.request.NearbyQuery
import io.proj4.ezgas.request.PageQuery
import io.proj4.ezgas.response.mappers.asResponse
import org.springframework.stereotype.Service

@Service
class StationService(private val repository: StationRepository) {

    fun findById(id: Int) = repository
            .findById(id)
            ?.asResponse()
            ?: throw ResourceNotFoundException("No station found with id: $id")

    fun findByIdsAndFuels(ids: Set<Int>, fuelTypes: Set<FuelType>?) = repository
            .findByIdsAndFuels(ids, fuelTypes ?: FuelType.values().toSet())
            .ifEmpty { throw ResourceNotFoundException("No stations found with ids $ids") }
            .map(Station::asResponse)

    fun findNearby(nearbyQuery: NearbyQuery, pageQuery: PageQuery) = repository
            .findNearby(nearbyQuery, pageQuery)
            ?: throw ResourceNotFoundException("No stations found at current location")
}