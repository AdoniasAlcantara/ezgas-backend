package io.proj4.ezgas.controller

import io.proj4.ezgas.model.FuelType
import io.proj4.ezgas.request.NearbyQuery
import io.proj4.ezgas.request.PageQuery
import io.proj4.ezgas.service.StationService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotEmpty

@RestController
@RequestMapping("/stations")
@ResponseStatus(HttpStatus.OK)
@Validated
class StationController(private val service: StationService) {

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int) = service.findById(id)

    @GetMapping
    fun getByIds(
            @RequestParam @NotEmpty ids: Set<Int>,
            @RequestParam(defaultValue = "") fuelTypes: Array<FuelType>
    ) = service.findByIds(ids, fuelTypes)

    @GetMapping("/nearby")
    fun getNearby(
            @Valid nearbyQuery: NearbyQuery,
            @Valid pageQuery: PageQuery
    ) = service.findNearby(nearbyQuery, pageQuery)
}