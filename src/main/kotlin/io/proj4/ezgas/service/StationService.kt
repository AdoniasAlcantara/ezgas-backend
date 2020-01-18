package io.proj4.ezgas.service

import io.proj4.ezgas.error.ResourceNotFoundException
import io.proj4.ezgas.model.Station
import io.proj4.ezgas.repository.StationRepository
import io.proj4.ezgas.request.NearbyStationsQuery
import io.proj4.ezgas.request.StationsByIdQuery
import io.proj4.ezgas.response.FuelDto
import io.proj4.ezgas.response.StationDto
import io.proj4.ezgas.util.isoDateTime
import org.springframework.stereotype.Service

@Service
class StationService(private val repository: StationRepository) {

    fun findById(query: StationsByIdQuery) = repository.findById(query)
            .ifEmpty { throw ResourceNotFoundException("No stations found with ids: ${query.ids}") }
            .map(this::toDto)

    fun findNearby(query: NearbyStationsQuery) = repository.findNearby(query)
            .ifEmpty { throw ResourceNotFoundException("No stations found at current position") }
            .map(this::toDto)

    private fun toDto(entity: Station) = with(entity) {
        val (brandId, brandName) = with(brand) { id to name }

        val (latitude, longitude, address) = with(location) {
            Triple(
                    latitude,
                    longitude,
                    arrayOf(number, address, area).joinToString()
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
}