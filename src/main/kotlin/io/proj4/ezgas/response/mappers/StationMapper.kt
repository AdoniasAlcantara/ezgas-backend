package io.proj4.ezgas.response.mappers

import io.proj4.ezgas.model.Station
import io.proj4.ezgas.response.FuelDto
import io.proj4.ezgas.response.StationDto
import io.proj4.ezgas.response.StationWithDistanceDto
import io.proj4.ezgas.util.isoDateTimeString
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

    val (city, state) = with(location) { Pair(city.name, city.state.name) }

    val fuels = fuels.associate { fuel ->
        Pair(
                fuel.key.type.simpleName,
                FuelDto(
                        fuel.salePrice.toString(),
                        fuel.purchasePrice?.toString(),
                        fuel.updated.isoDateTimeString,
                        fuel.source
                )
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