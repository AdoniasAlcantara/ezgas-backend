package io.proj4.ezgas.resource

import io.proj4.ezgas.dto.GasStationDto
import io.proj4.ezgas.request.GetNearbyRequest
import io.proj4.ezgas.service.GasStationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/gasStations")
class GasStationResource(private val service: GasStationService) {

    @GetMapping("/{ids}")
    fun getByIds(@PathVariable ids: List<Int>): ResponseEntity<List<GasStationDto>> {
        val gasStations = service.findGasStationsByIds(ids)
        return ResponseEntity.ok(gasStations)
    }

    @GetMapping("/nearby")
    fun getNearby(@Valid request: GetNearbyRequest): ResponseEntity<List<GasStationDto>> {
        val gasStations = service.findNearbyGasStations(request)
        return ResponseEntity.ok(gasStations)
    }
}