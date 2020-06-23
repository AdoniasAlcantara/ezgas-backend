package io.proj4.ezgas.error

data class ErrorDetail(
        val field: String,
        val error: String?,
        val value: Any?
)