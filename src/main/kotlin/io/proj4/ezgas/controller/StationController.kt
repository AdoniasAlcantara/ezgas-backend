package io.proj4.ezgas.controller

import io.proj4.ezgas.model.FuelType
import io.proj4.ezgas.request.NearbyQuery
import io.proj4.ezgas.request.PageQuery
import io.proj4.ezgas.service.StationService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/stations")
class StationController(private val service: StationService) {

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int) = service.findById(id)

    @GetMapping
    fun getByIds(
            @RequestParam ids: Set<Int>,
            @RequestParam fuels: Set<FuelType>?
    ) = service.findByIdsAndFuels(ids, fuels)

    @GetMapping("/nearby")
    fun getNearby(
            @Valid nearbyQuery: NearbyQuery,
            @Valid pageQuery: PageQuery
    ) = service.findNearby(nearbyQuery, pageQuery)
}