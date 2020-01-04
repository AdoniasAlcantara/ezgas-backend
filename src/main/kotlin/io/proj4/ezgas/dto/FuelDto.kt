package io.proj4.ezgas.dto

import io.proj4.ezgas.model.Fuel

class FuelDto(
        val type: Fuel.Type,
        val price: String
)