package io.proj4.ezgas.request

import io.proj4.ezgas.model.FuelType
import io.proj4.ezgas.model.SortCriteria
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class StringToFuelTypeConverter : Converter<String, FuelType> {
    override fun convert(source: String) = FuelType.valueOf(source.toUpperCase())
}

@Component
class StringToSortCriteriaConverter : Converter<String, SortCriteria> {
    override fun convert(source: String) = SortCriteria.valueOf(source.toUpperCase())
}