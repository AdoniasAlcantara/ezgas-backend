package io.proj4.ezgas.repository

import io.proj4.ezgas.model.Brand
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BrandRepository : JpaRepository<Brand, Int>