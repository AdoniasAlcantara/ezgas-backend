package io.proj4.ezgas.model

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity

@Entity
data class Fuel(
        @EmbeddedId
        val key: FuelKey,

        @Column(precision = 10, scale = 2)
        val salePrice: BigDecimal,

        @Column(precision = 10, scale = 2)
        val purchasePrice: BigDecimal?,
        val updated: LocalDateTime,
        val source: String
)