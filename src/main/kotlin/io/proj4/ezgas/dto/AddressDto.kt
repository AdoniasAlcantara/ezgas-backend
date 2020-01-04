package io.proj4.ezgas.dto

class AddressDto(
        val latitude: Double,
        val longitude: Double,
        val address: String?,
        val number: String?,
        val neighborhood: String?,
        val city: String,
        val state: String
)