package io.proj4.ezgas.model

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import io.proj4.ezgas.configuration.conversion.ObjectIdConverter
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDate

@Document
data class FuelHistory(
    val id: String,
    val date: LocalDate,
    val fuelType: FuelType,
    val price: BigDecimal,

    @JsonSerialize(converter = ObjectIdConverter::class)
    val stationId: ObjectId
)