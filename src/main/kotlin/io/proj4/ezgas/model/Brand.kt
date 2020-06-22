package io.proj4.ezgas.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id

@Entity
data class Brand(
        @Id
        @GeneratedValue(strategy = IDENTITY)
        @Column(name = "brandId")
        val id: Int,
        val name: String
)