package io.proj4.ezgas.service

import io.proj4.ezgas.error.ResourceNotFoundException
import io.proj4.ezgas.model.FuelType
import io.proj4.ezgas.model.Station
import io.proj4.ezgas.repository.StationRepository
import io.proj4.ezgas.request.NearbyRequest
import io.proj4.ezgas.response.FuelDto
import io.proj4.ezgas.response.PageDto
import io.proj4.ezgas.response.StationDto
import io.proj4.ezgas.response.StationWithDistanceDto
import io.proj4.ezgas.util.isoDateTime
import io.proj4.ezgas.util.joinNotNullToString
import io.proj4.ezgas.util.toPage
import org.springframework.stereotype.Service

@Service
class StationService(private val repository: StationRepository) {

    fun findById(id: Int) = repository
            .findById(id)
            ?.let(this::toDto)
            ?: throw ResourceNotFoundException("No station found with id: $id")

    fun findByIds(ids: Set<Int>, fuelTypes: Array<FuelType>) = repository
            .findByIds(ids, *fuelTypes)
            .ifEmpty { throw ResourceNotFoundException("No stations found with ids: $ids") }
            .map(this::toDto)

    // This function will be removed soon
    fun findNearby(nearbyRequest: NearbyRequest) = with(nearbyRequest) {
        val idsWithDistance = repository
                .findIdsWithDistance(nearbyRequest)
                .ifEmpty { throw ResourceNotFoundException("No stations found at current location") }
                .associate { it.id to it.distance }

        return@with repository
                .findByIds(idsWithDistance.keys, fuelType)
                .map { toDto(it, idsWithDistance) }
    }

    fun findNearby(nearbyRequest: NearbyRequest, pageNumber: Int, pageSize: Int) = with(nearbyRequest) {
        val idsWithDistance = repository
                .findIdsWithDistance(nearbyRequest)
                .ifEmpty { throw ResourceNotFoundException("No stations found at current location") }
                .associate { it.id to it.distance }

        val page = idsWithDistance
                .keys
                .toPage(pageNumber, pageSize)

        val dtoList = repository
                .findByIds(page.items, fuelType)
                .map { toDto(it, idsWithDistance) }

        return@with with(page) {
            PageDto(number, size, totalItems, totalPages, hasNext, dtoList)
        }
    }

    private fun toDto(entity: Station) = with(entity) {
        val (brandId, brandName) = with(brand) { id to name }

        val (latitude, longitude, address) = with(location) {
            Triple(
                    latitude,
                    longitude,
                    arrayOf(number, address, area).joinNotNullToString()
            )
        }

        val (city, state) = with(location) { city.name to city.state.name }

        val fuels = fuels.associate {
            it.key.type.simpleName to FuelDto(
                    it.updated.isoDateTime,
                    it.price.toString()
            )
        }

        return@with StationDto(id, company, brandId, brandName, latitude, longitude, address, city, state, fuels)
    }

    private fun toDto(entity: Station, idsWithDistance: Map<Int, Float>) = StationWithDistanceDto(
            toDto(entity),
            idsWithDistance.getOrDefault(entity.id, 0f)
    )
}