package io.proj4.ezgas.response

data class FuelDto(
        val salePrice: String,
        val purchasePrice: String?,
        val updated: String,
        val source: String
)