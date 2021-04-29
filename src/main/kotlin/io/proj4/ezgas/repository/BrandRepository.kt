package io.proj4.ezgas.repository

import io.proj4.ezgas.model.Brand
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface BrandRepository : MongoRepository<Brand, String>
