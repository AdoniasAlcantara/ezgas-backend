package io.proj4.ezgas.controller

import io.proj4.ezgas.repository.BrandRepository
import org.springframework.http.CacheControl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping("/brands")
class BrandController(private val repository: BrandRepository) {

    @GetMapping
    fun getAll() =
        ResponseEntity.ok()
            .cacheControl(CacheControl.maxAge(7L, TimeUnit.DAYS))
            .body(repository.findAll())
}
