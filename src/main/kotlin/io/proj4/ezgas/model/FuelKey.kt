package io.proj4.ezgas.model

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EnumType.STRING
import javax.persistence.Enumerated
import javax.persistence.JoinColumn

@Embeddable
data class FuelKey(
        @Column(name = "stationId")
        val id: Int,

        @Enumerated(STRING)
        @Column(columnDefinition = "CHAR(16)")
        val type: FuelType
) : Serializable