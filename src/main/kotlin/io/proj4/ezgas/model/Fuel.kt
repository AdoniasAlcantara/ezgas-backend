package io.proj4.ezgas.model

import java.io.Serializable
import java.math.BigDecimal
import java.time.OffsetDateTime

data class Fuel(
        val key: FuelKey,
        val salePrice: BigDecimal,
        val purchasePrice: BigDecimal?,
        val updated: OffsetDateTime,
        val source: String
)

data class FuelKey(
        val id: Int,
        val type: FuelType
) : Serializable

enum class FuelType(val simpleName: String) {
    GASOLINE("gasoline"),
    ETHANOL("ethanol"),
    DIESEL("diesel"),
    DIESEL_S10("dieselS10")
}
