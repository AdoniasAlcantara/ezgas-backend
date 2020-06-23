package io.proj4.ezgas.model

import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.OffsetDateTime
import javax.persistence.*
import javax.persistence.EnumType.STRING

@Entity
data class Fuel(
        @EmbeddedId
        val key: FuelKey,

        @Column(precision = 10, scale = 2)
        val salePrice: BigDecimal,

        @Column(precision = 10, scale = 2)
        val purchasePrice: BigDecimal?,
        val updated: OffsetDateTime,
        val source: String
)

@Embeddable
data class FuelKey(
        @Column(name = "stationId")
        val id: Int,

        @Enumerated(STRING)
        @Column(name = "fuelType")
        val type: FuelType
) : Serializable

enum class FuelType(val simpleName: String) {
    GASOLINE("gasoline"),
    ETHANOL("ethanol"),
    DIESEL("diesel"),
    DIESEL_S10("dieselS10")
}