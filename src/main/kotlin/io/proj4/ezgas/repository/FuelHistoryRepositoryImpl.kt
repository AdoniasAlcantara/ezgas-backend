package io.proj4.ezgas.repository

import io.proj4.ezgas.model.FuelHistory
import io.proj4.ezgas.model.FuelType
import org.bson.types.ObjectId
import org.springframework.data.domain.Sort.Direction.ASC
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.aggregation.Aggregation.*
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class FuelHistoryRepositoryImpl(private val mongo: MongoOperations) : FuelHistoryRepository {

    override fun find(
        stationId: ObjectId,
        fuelType: FuelType,
        startDate: LocalDate,
        endDate: LocalDate
    ): List<FuelHistory> {
        val aggregation = newAggregation(
            FuelHistory::class.java,
            match(
                where(FuelHistory::stationId.name).isEqualTo(stationId)
                    .and(FuelHistory::fuelType.name).isEqualTo(fuelType)
                    .and(FuelHistory::date.name).gte(startDate).lte(endDate)
            ),
            sort(ASC, FuelHistory::date.name)
        )

        return mongo.aggregate(aggregation, FuelHistory::class.java).mappedResults
    }
}