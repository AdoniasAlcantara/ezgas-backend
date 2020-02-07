package io.proj4.ezgas.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val LocalDateTime.isoDateTime: String
    get() = format(DateTimeFormatter.ISO_DATE_TIME)

fun Array<*>.joinNotNullToString() = filterNotNull().joinToString()