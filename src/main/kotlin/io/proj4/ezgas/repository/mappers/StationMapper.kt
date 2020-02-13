package io.proj4.ezgas.repository.mappers

import io.proj4.ezgas.model.Station
import io.proj4.ezgas.response.FuelDto
import io.proj4.ezgas.response.StationDto
import io.proj4.ezgas.response.StationWithDistanceDto
import io.proj4.ezgas.util.isoDateTime
import io.proj4.ezgas.util.joinNotNullToString

fun Station.toDto(): StationDto {
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

    return StationDto(id, company, brandId, brandName, latitude, longitude, address, city, state, fuels)
}

fun List<Station>.joinWithDistance(idsWithDistance: Map<Int, Float>) = map {
    StationWithDistanceDto(
            it.toDto(),
            idsWithDistance.getOrDefault(it.id, 0f)
    )
}