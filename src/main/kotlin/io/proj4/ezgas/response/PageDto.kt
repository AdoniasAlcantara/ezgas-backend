package io.proj4.ezgas.response

data class PageDto<T>(
        val pageNumber: Int,
        val pageSize: Int,
        val totalItems: Int,
        val totalPages: Int,
        val hasNext: Boolean,
        val items: List<T>
)