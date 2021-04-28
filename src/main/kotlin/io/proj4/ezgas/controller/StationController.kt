package io.proj4.ezgas.controller

import io.proj4.ezgas.request.NearbyQuery
import io.proj4.ezgas.request.Paging
import io.proj4.ezgas.service.StationService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/stations")
class StationController(private val service: StationService) {

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String) =
        service.findById(id)

    @GetMapping
    fun getAllById(@RequestParam ids: Set<String>) =
        service.findAllById(ids)

    @GetMapping("/nearby")
    fun getNearby(@Valid query: NearbyQuery, @Valid paging: Paging) =
        service.findNearby(query, paging)
}
