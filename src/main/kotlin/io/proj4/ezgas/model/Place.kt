package io.proj4.ezgas.model

data class Place(
    val houseNumber: String?,
    val street: String?,
    val neighborhood: String?,
    val city: String,
    val state: String,
    val postalCode: String
)
