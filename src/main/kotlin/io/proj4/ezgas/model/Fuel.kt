package io.proj4.ezgas.model

import java.math.BigDecimal
import java.time.LocalDate

data class Fuel(
    val updatedAt: LocalDate,
    val price: BigDecimal
)
