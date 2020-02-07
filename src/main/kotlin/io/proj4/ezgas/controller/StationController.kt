package io.proj4.ezgas.controller

import io.proj4.ezgas.model.FuelType
import io.proj4.ezgas.request.NearbyRequest
import io.proj4.ezgas.service.StationService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min
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
    fun getNearby(@Valid nearbyRequest: NearbyRequest) = service.findNearby(nearbyRequest)

    @GetMapping("/nearby/page")
    fun getNearby(
            @Valid nearbyRequest: NearbyRequest,
            @RequestParam @Min(1) pageNumber: Int,
            @RequestParam(defaultValue = "10") @Min(1) @Max(50) pageSize: Int
    ) = service.findNearby(nearbyRequest, pageNumber, pageSize)
}