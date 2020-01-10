package io.proj4.ezgas.model

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.EmbeddedId
import javax.persistence.Entity

@Entity
data class Fuel(
        @EmbeddedId
        val key: FuelKey,
        val updated: LocalDateTime,
        val price: BigDecimal
)