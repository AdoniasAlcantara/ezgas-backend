package io.proj4.ezgas.response

import com.fasterxml.jackson.annotation.JsonAnyGetter

class StationDto(
        val id: Int,
        val company: String,
        val brandId: Int,
        val brandName: String,
        val latitude: Double,
        val longitude: Double,
        val address: String,
        val city: String,
        val state: String,

        @get:JsonAnyGetter
        val fuels: Map<String, FuelDto>
)