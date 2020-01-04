package io.proj4.ezgas.model

import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.FetchType.EAGER
import javax.persistence.GenerationType.IDENTITY

@Entity
data class GasStation(
        @Id @GeneratedValue(strategy = IDENTITY)
        val id: Int,
        val company: String,
        val lastUpdate: LocalDateTime,

        @ManyToOne @JoinColumn(name = "brandId")
        val brand: Brand,

        @Embedded
        val address: Address,

        @OneToMany @JoinColumn(name = "gasStationId")
        val fuels: Set<Fuel>
)