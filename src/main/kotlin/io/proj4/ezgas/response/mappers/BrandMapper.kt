package io.proj4.ezgas.response.mappers

import io.proj4.ezgas.model.Brand
import io.proj4.ezgas.response.BrandDto

fun Brand.toDto() = BrandDto(id, name)