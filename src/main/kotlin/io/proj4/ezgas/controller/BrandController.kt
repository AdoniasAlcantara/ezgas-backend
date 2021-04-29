package io.proj4.ezgas.controller

import io.proj4.ezgas.model.Brand
import io.proj4.ezgas.repository.BrandRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/brands")
class BrandController(private val repository: BrandRepository) {

    @GetMapping
    fun getAll(): List<Brand> = repository.findAll()
}
