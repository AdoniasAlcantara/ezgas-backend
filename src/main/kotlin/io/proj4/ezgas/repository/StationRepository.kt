package io.proj4.ezgas.repository

import io.proj4.ezgas.model.Station
import org.springframework.data.mongodb.repository.MongoRepository

interface StationRepository : MongoRepository<Station, String>
