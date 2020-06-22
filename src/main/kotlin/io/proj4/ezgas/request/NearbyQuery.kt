package io.proj4.ezgas.request

import io.proj4.ezgas.model.FuelType
import io.proj4.ezgas.model.SortCriteria
import org.hibernate.validator.constraints.Range
import javax.validation.constraints.NotNull

data class NearbyQuery(
        @field:NotNull
        @field:Range(min = -90, max = 90)
        val latitude: Double? = null,

        @field:NotNull
        @field:Range(min = -180, max = 180)
        val longitude: Double? = null,

        @field:NotNull
        @field:Range(min = 100, max = 25000)
        val distance: Float? = 10000f,

        @field:NotNull
        val fuel: FuelType? = null,

        @NotNull
        val sort: SortCriteria? = SortCriteria.PRICE
)