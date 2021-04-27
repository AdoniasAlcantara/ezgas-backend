package io.proj4.ezgas.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import io.proj4.ezgas.configuration.conversion.GeoJsonConverter
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.mapping.Document

@Document("stations")
data class Station(
    @Id
    val id: String,
    val company: String,
    val brand: Brand,
    val fuels: Map<FuelType, Fuel>?,
    val place: Place?,

    @JsonSerialize(converter = GeoJsonConverter::class)
    val position: GeoJsonPoint,

    @JsonInclude(NON_NULL)
    val distance: Double?
)
