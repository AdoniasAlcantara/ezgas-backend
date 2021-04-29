package io.proj4.ezgas.model

import org.springframework.data.mongodb.core.mapping.Document

@Document("brands")
data class Brand(
    val id: String,
    val name: String
)
