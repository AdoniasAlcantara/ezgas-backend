package io.proj4.ezgas.request

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class Paging(
    @field:NotNull
    @field:Min(0)
    val pageNumber: Int? = 0,

    @NotNull
    @Min(1) @Max(50)
    val pageSize: Int? = 24
)
