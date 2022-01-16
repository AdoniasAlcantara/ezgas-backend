package io.proj4.ezgas.repository

import io.proj4.ezgas.model.FuelHistory
import io.proj4.ezgas.model.FuelType
import org.bson.types.ObjectId
import java.time.LocalDate

interface FuelHistoryRepository {

    fun find(stationId: ObjectId, fuelType: FuelType, startDate: LocalDate, endDate: LocalDate): List<FuelHistory>
}