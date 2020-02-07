package io.proj4.ezgas.error

open class Error(
        val status: Int,
        val path: String,
        val message: String
)