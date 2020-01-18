package io.proj4.ezgas.resource

import io.proj4.ezgas.repository.BrandRepository
import io.proj4.ezgas.response.BrandDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/brands")
class BrandResource(private val repository: BrandRepository) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAll() = repository.findAll().map { BrandDto(it.id, it.name) }
}