package io.proj4.ezgas.configuration.conversion

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class StringSetConverter : Converter<String, Set<String>> {

    override fun convert(input: String): Set<String> =
        input.split(",").toSet()
}
