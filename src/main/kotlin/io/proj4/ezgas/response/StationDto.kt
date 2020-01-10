package io.proj4.ezgas.response

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
        val fuels: List<FuelDto>
)