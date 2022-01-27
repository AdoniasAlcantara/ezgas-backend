package io.proj4.ezgas.controller

import io.proj4.ezgas.request.HistoryQuery
import io.proj4.ezgas.request.NearbyQuery
import io.proj4.ezgas.request.Paging
import io.proj4.ezgas.service.StationService
import org.springframework.http.CacheControl.maxAge
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.concurrent.TimeUnit
import javax.validation.Valid

@RestController
@RequestMapping("/stations")
class StationController(private val service: StationService) {

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String) =
        ResponseEntity.ok()
            .cacheControl(maxAge(1L, TimeUnit.DAYS))
            .body(service.findById(id))

    @GetMapping
    fun getAllById(@RequestParam ids: Set<String>) =
        ResponseEntity.ok()
            .cacheControl(maxAge(1L, TimeUnit.DAYS))
            .body(service.findAllById(ids))

    @GetMapping("/nearby")
    fun getNearby(@Valid query: NearbyQuery, @Valid paging: Paging) =
        service.findNearby(query, paging)

    @GetMapping("/{id}/fuelHistory")
    fun getFuelHistory(@PathVariable id: String, @Valid query: HistoryQuery) =
        ResponseEntity.ok()
            .cacheControl(maxAge(1L, TimeUnit.DAYS))
            .body(service.findFuelHistory(id, query))
}
