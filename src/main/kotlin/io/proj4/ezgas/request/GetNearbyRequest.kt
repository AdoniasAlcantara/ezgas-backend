package io.proj4.ezgas.request

import io.proj4.ezgas.model.Fuel
import io.proj4.ezgas.model.SortCriteria
import org.hibernate.validator.constraints.Range
import java.lang.IllegalArgumentException

class GetNearbyRequest(
        @field:Range(min = -90, max = 90)
        val latitude: Double,

        @field:Range(min = -180, max = 180)
        val longitude: Double,

        @field:Range(min = 1, max = 25)
        val range: Float = 15f,
        val fuelType: Fuel.Type,
        val brands: String = "",
        val sortBy: SortCriteria = SortCriteria.PRICE
) {
    val brandsAsIntList: List<Int> = if (brands.isNotBlank()) {
        brands.split(",").map(String::toInt)
    } else emptyList()
}