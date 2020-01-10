package io.proj4.ezgas.resource

import io.proj4.ezgas.request.NearbyQuery
import io.proj4.ezgas.service.StationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/stations")
class StationResource(private val service: StationService) {

    @GetMapping("/{ids}")
    fun getByIds(@PathVariable ids: List<Int>) = ResponseEntity.ok(service.findByIds(ids))

    @GetMapping("/nearby")
    fun getNearby(@Valid query: NearbyQuery) = ResponseEntity.ok(service.findNearby(query))
}