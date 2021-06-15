package io.proj4.ezgas.utils

import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import kotlin.math.*

infix fun GeoJsonPoint.distanceTo(other: GeoJsonPoint): Double {
    val latDist = Math.toRadians(this.y - other.y)
    val lngDist = Math.toRadians(this.x - other.x)

    val a = sin(latDist / 2).pow(2) +
            cos(Math.toRadians(this.y)) *
            cos(Math.toRadians(other.y)) *
            sin(lngDist / 2).pow(2)

    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return round(6371 * c)
}
