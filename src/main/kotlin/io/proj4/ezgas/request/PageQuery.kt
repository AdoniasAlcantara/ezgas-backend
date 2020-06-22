package io.proj4.ezgas.request

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class PageQuery(
        @field:NotNull
        @field:Min(0)
        val pageNumber: Int? = null,

        @NotNull
        @Min(1) @Max(50)
        val pageSize: Int? = 24
)