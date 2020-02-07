package io.proj4.ezgas.response

import com.fasterxml.jackson.annotation.JsonUnwrapped

data class StationWithDistanceDto(
        @JsonUnwrapped
        val station: StationDto,
        val distance: Float
)