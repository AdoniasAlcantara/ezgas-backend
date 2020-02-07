package io.proj4.ezgas.util

import io.proj4.ezgas.error.PageNotFoundException
import kotlin.math.ceil

class Page<T>(pageNumber: Int, pageSize: Int, pageItems: List<T>) {
    val number: Int
    val size: Int
    val totalItems: Int
    val totalPages: Int
    val hasNext: Boolean
    val items: List<T>

    init {
        val start = (pageNumber - 1) * pageSize
        val end = run {
            val temp = start + pageSize - 1
            return@run if (temp <= pageItems.lastIndex) temp else pageItems.lastIndex
        }

        items = pageItems.slice(start..end).ifEmpty { throw PageNotFoundException(pageNumber) }
        number = pageNumber
        size = items.count()
        totalItems = pageItems.count()
        totalPages =  ceil(totalItems.toDouble() / pageSize).toInt()
        hasNext = end < pageItems.lastIndex
    }
}

fun <T> Set<T>.toPage(pageNumber: Int, pageSize: Int) = with(toList()) { toPage(pageNumber, pageSize) }

fun <T> List<T>.toPage(pageNumber: Int, pageSize: Int): Page<T> = Page(pageNumber, pageSize, this)