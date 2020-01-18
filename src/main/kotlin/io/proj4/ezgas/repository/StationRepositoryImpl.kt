package io.proj4.ezgas.repository

import io.proj4.ezgas.model.*
import io.proj4.ezgas.model.SortCriteria.PRICE
import io.proj4.ezgas.request.NearbyStationsQuery
import io.proj4.ezgas.request.StationsByIdQuery
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import javax.persistence.EntityManager
import javax.persistence.criteria.*

@Repository
@Suppress("UNCHECKED_CAST")
class StationRepositoryImpl(private val entityManager: EntityManager) : StationRepository {

    override fun findById(requestQuery: StationsByIdQuery): List<Station> = with(requestQuery) {
        val (_, query, root) = createQuery()
        val predicates = mutableListOf<Predicate>(root.get<Int>("id").`in`(ids))

        root.run {
            fetch<Station, Brand>("brand")
            fetch<Station, Location>("location")
                    .fetch<Location, City>("city")
                    .fetch<City, State>("state")
        }

        val fuelJoin = root.fetch<Station, Fuel>("fuels") as Join<Station, Fuel>
        fuelType?.let { predicates.add(fuelJoin.get<FuelKey>("key").get<FuelType>("type").`in`(fuelType)) }
        query.distinct(true).where(*predicates.toTypedArray())

        return entityManager.createQuery(query).resultList
    }

    override fun findNearby(requestQuery: NearbyStationsQuery): List<Station> = with(requestQuery) {
        val (builder, query, root) = createQuery()
        val predicates = mutableListOf<Predicate>()

        root.fetch<Station, Location>("location")
                .fetch<Location, City>("city")
                .fetch<City, State>("state")

        val brandJoin = root.fetch<Station, Brand>("brand") as Join<Station, Brand>
        brands?.let { predicates.add(brandJoin.`in`(it)) }

        val fuelJoin = root.fetch<Station, Fuel>("fuels") as Join<Station, Fuel>
        predicates.add(builder.equal(fuelJoin.get<FuelKey>("key").get<FuelType>("type"), fuelType))

        val locationPath = root.get<Location>("location")
        val distanceFunction = builder.function(
                "distance",
                Float::class.java,
                locationPath.get<Double>("latitude"),
                locationPath.get<Double>("longitude"),
                builder.literal(latitude),
                builder.literal(longitude)
        )

        predicates.add(builder.lessThanOrEqualTo(distanceFunction, distance))

        val order = if (sortBy == PRICE) {
            builder.asc(fuelJoin.get<BigDecimal>("price"))
        } else {
            builder.asc(distanceFunction)
        }

        query.distinct(true)
                .where(*predicates.toTypedArray())
                .orderBy(order)

        return entityManager.createQuery(query).resultList
    }

    private fun createQuery(): Triple<CriteriaBuilder, CriteriaQuery<Station>, Root<Station>> {
        val builder = entityManager.criteriaBuilder
        val query = builder.createQuery(Station::class.java)
        val root = query.from(Station::class.java)

        return Triple(builder, query, root)
    }
}