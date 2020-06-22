package io.proj4.ezgas.response.mappers

import io.proj4.ezgas.model.Station
import io.proj4.ezgas.response.FuelResponse
import io.proj4.ezgas.response.StationResponse
import io.proj4.ezgas.response.StationWithDistance
import io.proj4.ezgas.util.isoDateTimeString
import io.proj4.ezgas.util.joinNotNullToString

fun Station.asResponse(): StationResponse {
    val (brandId, brandName) = brand
    val latitude = location.latitude
    val longitude = location.longitude
    val address = with(location) { arrayOf(number, address, area).joinNotNullToString() }
    val (city, state) = with(location.city) { name to state.name }
    val fuels = fuels.associate { fuel ->
        val fuelKey = fuel.key.type.simpleName
        val fuelValue = FuelResponse(
                fuel.salePrice.toString(),
                fuel.purchasePrice?.toString(),
                fuel.updated.isoDateTimeString,
                fuel.source
        )

        fuelKey to fuelValue
    }

    return StationResponse(id, company, brandId, brandName, latitude, longitude, address, city, state, fuels)
}

fun List<Station>.joinWithDistance(idsWithDistance: Map<Int, Float>) = map {
    StationWithDistance(
            station = it.asResponse(),
            distance = idsWithDistance[it.id] ?: 0f
    )
}