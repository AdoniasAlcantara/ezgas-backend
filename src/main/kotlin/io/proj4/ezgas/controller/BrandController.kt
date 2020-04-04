package io.proj4.ezgas.controller

import io.proj4.ezgas.model.Brand
import io.proj4.ezgas.repository.BrandRepository
import io.proj4.ezgas.response.mappers.toDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/brands")
@ResponseStatus(HttpStatus.OK)
class BrandController(private val repository: BrandRepository) {

    @GetMapping
    fun getAll() = repository.findAll().map(Brand::toDto)
}