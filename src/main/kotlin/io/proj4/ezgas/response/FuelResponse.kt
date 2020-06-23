package io.proj4.ezgas.response

import java.time.OffsetDateTime

data class FuelResponse(
        val salePrice: String,
        val purchasePrice: String?,
        val updated: OffsetDateTime,
        val source: String
)