package io.proj4.ezgas.utils

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

fun <T> Set<T>.slice(pageable: Pageable) = toList().slice(pageable)

fun <T> List<T>.slice(pageable: Pageable): List<T> {
    val start = pageable.offset.toInt()
    val end = minOf(start + pageable.pageSize, count())
    return slice(start until end)
}

fun <T> newPage(content: List<T>, pageable: Pageable, total: Int) = PageImpl(content, pageable, total.toLong())

