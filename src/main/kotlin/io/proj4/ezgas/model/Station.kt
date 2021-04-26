package io.proj4.ezgas.model

data class Station(
        val id: Int,
        val company: String,
        val brand: Brand,
        val location: Location,
        val fuels: Set<Fuel>
)
