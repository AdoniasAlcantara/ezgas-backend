package io.proj4.ezgas.repository

import io.proj4.ezgas.model.FuelHistory
import io.proj4.ezgas.model.FuelType
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDate

interface FuelHistoryRepository : MongoRepository<FuelHistory, String> {

    fun findByStationIdAndFuelTypeAndDateBetween(
        stationId: ObjectId,
        fuelType: FuelType,
        startDate: LocalDate,
        endDate: LocalDate
    ): List<FuelHistory>
}