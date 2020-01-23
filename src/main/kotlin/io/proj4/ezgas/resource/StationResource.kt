package io.proj4.ezgas.resource

import io.proj4.ezgas.request.NearbyStationsQuery
import io.proj4.ezgas.request.StationsByIdQuery
import io.proj4.ezgas.service.StationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/stations")
class StationResource(private val service: StationService) {

    @GetMapping("{id}")
    fun getById(@PathVariable id: Int) = ResponseEntity.ok(service.findById(id))

    @GetMapping
    fun getByIds(@Valid query: StationsByIdQuery) = ResponseEntity.ok(service.findByIds(query))

    @GetMapping("/nearby")
    fun getNearby(@Valid query: NearbyStationsQuery) = ResponseEntity.ok(service.findNearby(query))
}