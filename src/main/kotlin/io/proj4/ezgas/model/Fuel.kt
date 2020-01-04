package io.proj4.ezgas.model

import java.math.BigDecimal
import javax.persistence.*
import javax.persistence.EnumType.STRING
import javax.persistence.GenerationType.IDENTITY

@Entity
data class Fuel(
        @Id @GeneratedValue(strategy = IDENTITY)
        val id: Int,

        @Enumerated(STRING)
        val type: Type,
        val price: BigDecimal
) {
    enum class Type {
        GASOLINE, ETHANOL, DIESEL, DIESEL_S10
    }
}