package io.proj4.ezgas.dto

class GasStationDto(
        val id: Int,
        val company: String,
        val lastUpdate: String,
        val brand: BrandDto,
        val address: AddressDto,
        val fuels: Set<FuelDto>
)