package io.proj4.ezgas.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class State(
        @Id @Column(columnDefinition = "CHAR(2)")
        val code: String,
        val name: String
)