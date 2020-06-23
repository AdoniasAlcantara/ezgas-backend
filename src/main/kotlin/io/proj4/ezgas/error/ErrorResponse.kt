package io.proj4.ezgas.error

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import java.time.OffsetDateTime

data class ErrorResponse(
        val timestamp: OffsetDateTime,
        val status: Int,
        val error: String,
        val message: String,
        val path: String,

        @JsonInclude(NON_NULL)
        var details: List<ErrorDetail>?
)