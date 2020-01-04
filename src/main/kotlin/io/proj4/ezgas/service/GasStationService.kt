package io.proj4.ezgas.service

import io.proj4.ezgas.dto.AddressDto
import io.proj4.ezgas.dto.BrandDto
import io.proj4.ezgas.dto.FuelDto
import io.proj4.ezgas.dto.GasStationDto
import io.proj4.ezgas.error.ResourceNotFound
import io.proj4.ezgas.model.GasStation
import io.proj4.ezgas.repository.GasStationRepository
import io.proj4.ezgas.repository.GasStationSpecification
import io.proj4.ezgas.request.GetNearbyRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.format.DateTimeFormatter

@Service
class GasStationService(private val repository: GasStationRepository) {

    fun findGasStationsByIds(ids: List<Int>): List<GasStationDto> {
        val result = repository.findByIds(ids)

        if (result.isEmpty()) {
            throw ResourceNotFound("No gas stations found with ids: $ids")
        }

        return result.map(this::toDto)
    }

    fun findNearbyGasStations(request: GetNearbyRequest): List<GasStationDto> {
        val gasStationIds = with(request) {
            repository.findIdsByLocation(latitude, longitude, range)
        }

        if (gasStationIds.isEmpty()) {
            throw ResourceNotFound("No gas stations found at current location")
        }

        val result = with(request) {
            val brandIds = brandsAsIntList
            val specification = GasStationSpecification(gasStationIds, brandIds, fuelType, sortBy)
            return@with repository.findAll(specification)
        }

        if (result.isEmpty()) {
            throw ResourceNotFound("Query parameters do not match any gas station result")
        }

        return result.map(this::toDto)
    }

    private fun toDto(entity: GasStation) = with(entity) {
        val lastUpdate = lastUpdate.format(DateTimeFormatter.ISO_DATE_TIME)
        val fuelsDto = fuels.map { FuelDto(it.type, it.price.toString()) }.toSet()
        val brandDto = with(brand) { BrandDto(id, name) }
        val addressDto = with(address) {
            AddressDto(latitude, longitude, address, number, neighborhood, city.name, city.state.name)
        }

        return@with GasStationDto(id, company, lastUpdate, brandDto, addressDto, fuelsDto)
    }
}