package io.proj4.ezgas.repository

import io.proj4.ezgas.model.FuelType
import io.proj4.ezgas.model.Station
import io.proj4.ezgas.utils.aggregatePage
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.geo.Distance
import org.springframework.data.geo.Metrics.KILOMETERS
import org.springframework.data.geo.Point
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.aggregation.Aggregation.*
import org.springframework.data.mongodb.core.aggregation.UnsetOperation.unset
import org.springframework.data.mongodb.core.getCollectionName
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.NearQuery.near

@Suppress("unused")
class NearbyStationDaoImpl(private val mongo: MongoOperations) : NearbyStationDao {
    private val collection = mongo.getCollectionName<Station>()

    override fun findNearby(
        point: Point,
        distance: Double,
        fuelType: FuelType,
        pageable: Pageable
    ): Page<Station> {
        val aggregation = newAggregation(
            geoNear(
                near(point)
                    .spherical(true)
                    .maxDistance(Distance(distance, KILOMETERS))
                    .inKilometers(),
                "distance"
            ),
            match(
                where("fuels.$fuelType.price").ne(null)
            ),
            unset(
                *takeFuelFieldsExcept(fuelType)
            )
        )

        return mongo.aggregatePage(aggregation, pageable, collection)
    }

    private fun takeFuelFieldsExcept(fuelType: FuelType) =
        FuelType.values()
            .filterNot { it == fuelType }
            .map { "fuels.$it" }
            .toTypedArray()
}
