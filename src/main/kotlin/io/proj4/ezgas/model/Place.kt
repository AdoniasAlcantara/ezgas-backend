package io.proj4.ezgas.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import io.proj4.ezgas.configuration.conversion.GeoJsonConverter
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed

data class Place(
    val houseNumber: String?,
    val street: String?,
    val neighborhood: String?,
    val city: String,
    val state: String,
    val postalCode: String,

    @JsonSerialize(converter = GeoJsonConverter::class)
    @field:GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    val position: GeoJsonPoint,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    val distance: Double?
)
