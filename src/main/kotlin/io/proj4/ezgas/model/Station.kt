package io.proj4.ezgas.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("stations")
data class Station(
    @Id
    val id: String,
    val company: String,
    val brand: Brand,
    val fuels: Map<FuelType, Fuel>?,
    val place: Place?
)
