package io.proj4.ezgas.model

data class Location(
        val latitude: Double,
        val longitude: Double,
        val number: String?,
        val address: String?,
        val area: String?,
        val city: City
)
