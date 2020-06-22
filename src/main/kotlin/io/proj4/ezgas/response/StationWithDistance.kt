package io.proj4.ezgas.response

import com.fasterxml.jackson.annotation.JsonUnwrapped

data class StationWithDistance(
        @JsonUnwrapped
        val station: StationResponse,
        val distance: Float
)