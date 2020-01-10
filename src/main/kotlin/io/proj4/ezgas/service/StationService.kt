package io.proj4.ezgas.service

import io.proj4.ezgas.error.ResourceNotFoundException
import io.proj4.ezgas.model.Station
import io.proj4.ezgas.repository.StationRepository
import io.proj4.ezgas.repository.StationSpec
import io.proj4.ezgas.request.NearbyQuery
import io.proj4.ezgas.response.FuelDto
import io.proj4.ezgas.response.StationDto
import org.springframework.stereotype.Service
import java.time.format.DateTimeFormatter

@Service
class StationService(private val repository: StationRepository) {

    fun findByIds(ids: List<Int>) = repository.findByIds(ids)
            .ifEmpty { throw ResourceNotFoundException("No stations found with ids: $ids") }
            .map(this::toDto)

    fun findNearby(query: NearbyQuery) = with(query) {
        val stationIds = repository.findIdsByLocation(latitude, longitude, range)
                .ifEmpty { throw ResourceNotFoundException("No stations found at current location") }

        val spec = StationSpec(stationIds, brands, fuelType, sortBy)

        return@with repository.findAll(spec)
                .ifEmpty { throw ResourceNotFoundException("Query parameters do not match any gas station result") }
                .map(this@StationService::toDto)
    }

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

        val fuels = fuels.map {
            FuelDto(
                    it.key.type.name,
                    it.updated.format(DateTimeFormatter.ISO_DATE_TIME),
                    it.price.toString()
            )
        }

        return@with StationDto(id, company, brandId, brandName, latitude, longitude, address, city, state, fuels)
    }
}