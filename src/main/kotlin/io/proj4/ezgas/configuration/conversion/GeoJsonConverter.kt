package io.proj4.ezgas.configuration.conversion

import com.fasterxml.jackson.databind.util.StdConverter
import io.proj4.ezgas.model.Position
import org.springframework.data.mongodb.core.geo.GeoJsonPoint

class GeoJsonConverter : StdConverter<GeoJsonPoint, Position>() {

    override fun convert(point: GeoJsonPoint) =
        Position(latitude = point.y, longitude = point.x)
}
