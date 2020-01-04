package io.proj4.ezgas.model

import javax.persistence.Embeddable
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Embeddable
data class Address(
        val latitude: Double,
        val longitude: Double,
        val address: String?,
        val number: String?,
        val neighborhood: String?,

        @ManyToOne @JoinColumn(name = "cityId")
        val city: City
)