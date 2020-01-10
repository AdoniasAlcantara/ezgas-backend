package io.proj4.ezgas.error

class FieldError(
        val field: String,
        val error: String,
        val value: Any?
)