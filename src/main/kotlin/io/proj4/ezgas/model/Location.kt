package io.proj4.ezgas.model

import javax.persistence.Embeddable
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Embeddable
data class Location(
        val latitude: Double,
        val longitude: Double,
        val number: String?,
        val address: String?,
        val area: String?,

        @ManyToOne @JoinColumn(name = "cityId")
        val city: City
)