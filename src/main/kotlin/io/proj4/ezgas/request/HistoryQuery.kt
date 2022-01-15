package io.proj4.ezgas.request

import io.proj4.ezgas.model.FuelType
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.validation.constraints.NotNull

data class HistoryQuery(
    @field:NotNull
    val fuelType: FuelType? = null,

    @field:NotNull
    @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val startDate: LocalDate? = LocalDate.now().minusWeeks(1),

    @field:NotNull
    @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val endDate: LocalDate? = LocalDate.now()
)