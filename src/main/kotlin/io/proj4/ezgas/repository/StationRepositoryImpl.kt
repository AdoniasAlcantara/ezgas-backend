package io.proj4.ezgas.repository

import io.proj4.ezgas.model.FuelType
import io.proj4.ezgas.model.Station
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.data.geo.Distance
import org.springframework.data.geo.Metrics.KILOMETERS
import org.springframework.data.geo.Point
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.aggregate
import org.springframework.data.mongodb.core.aggregation.Aggregation.*
import org.springframework.data.mongodb.core.getCollectionName
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.NearQuery.near
import org.springframework.stereotype.Repository

@Repository
class StationRepositoryImpl(private val template: MongoOperations) : StationRepository {
    private val collection = template.getCollectionName<Station>()

    override fun findNearby(
        point: Point,
        distance: Double,
        fuelType: FuelType,
        pageable: Pageable
    ): Slice<Station> {
        val aggregation = newAggregation(
            geoNearOp(point, distance),
            priceNotNullOp(fuelType),
            *pagingOps(pageable)
        )

        val stations = template
            .aggregate<Station>(aggregation, collection)
            .mappedResults

        return SliceImpl(stations)
    }

    private fun geoNearOp(point: Point, distance: Double) =
        geoNear(
            near(point)
                .spherical(true)
                .maxDistance(Distance(distance, KILOMETERS))
                .inKilometers(),
            "distance"
        )

    private fun priceNotNullOp(fuelType: FuelType) =
        match(where("fuels.$fuelType.price").ne(null))

    private fun pagingOps(pageable: Pageable) =
        arrayOf(
            sort(pageable.sort),
            skip(pageable.offset),
            limit(pageable.pageSize.toLong())
        )
}
