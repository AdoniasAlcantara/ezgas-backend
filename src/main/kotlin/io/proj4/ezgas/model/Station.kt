package io.proj4.ezgas.model

import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY

@Entity
data class Station(
        @Id @GeneratedValue(strategy = IDENTITY)
        val id: Int,
        val company: String,

        @ManyToOne @JoinColumn(name = "brandId")
        val brand: Brand,

        @Embedded
        val location: Location,

        @OneToMany @JoinColumn(name = "stationId")
        val fuels: Set<Fuel>
)