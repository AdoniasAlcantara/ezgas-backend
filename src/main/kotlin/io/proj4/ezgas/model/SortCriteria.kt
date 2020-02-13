package io.proj4.ezgas.model

import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Order.asc

enum class SortCriteria {
    PRICE, DISTANCE;

    fun toSort() = Sort.by(
            asc(if (this == PRICE) "price" else "distance")
    )
}