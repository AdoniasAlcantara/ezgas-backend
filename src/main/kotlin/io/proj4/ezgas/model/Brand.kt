package io.proj4.ezgas.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id

@Entity
class Brand(
        @Id
        @GeneratedValue(strategy = IDENTITY)
        val id: Int,
        val name: String
)