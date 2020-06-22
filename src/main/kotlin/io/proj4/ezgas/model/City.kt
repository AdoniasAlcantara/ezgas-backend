package io.proj4.ezgas.model

import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY

@Entity
data class City(
        @Id
        @GeneratedValue(strategy = IDENTITY)
        @Column(name = "cityId")
        val id: Int,
        val name: String,

        @ManyToOne
        @JoinColumn(name = "stateCode")
        val state: State
)