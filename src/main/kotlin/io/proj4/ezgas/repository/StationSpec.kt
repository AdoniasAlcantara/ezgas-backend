package io.proj4.ezgas.repository

import io.proj4.ezgas.model.*
import org.springframework.data.jpa.domain.Specification
import java.math.BigDecimal
import javax.persistence.criteria.*

class StationSpec(
        private val stationIds: List<Int>,
        private val brandIds: Set<Int>?,
        private val fuelType: FuelType,
        private val sortCriteria: SortCriteria
) : Specification<Station> {

    override fun toPredicate(root: Root<Station>, query: CriteriaQuery<*>, builder: CriteriaBuilder): Predicate? {
        query.distinct(true)

        @Suppress("UNCHECKED_CAST")
        val fuelJoin = root.fetch<Station, Fuel>("fuels") as Join<Station, Fuel>

        root.run {
            fetch<Station, Brand>("brand")
            fetch<Station, Location>("location")
                    .fetch<Location, City>("city")
                    .fetch<City, State>("state")
        }

        var predicate = builder.and(
                root.get<Int>("id").`in`(stationIds),
                builder.equal(fuelJoin.get<FuelKey>("key").get<FuelType>("type"), fuelType)
        )

        brandIds?.let {
            predicate = builder.and(predicate, root.get<Brand>("brand").`in`(brandIds))
        }

        if (sortCriteria == SortCriteria.PRICE) {
            query.orderBy(builder.asc(fuelJoin.get<BigDecimal>("price")))
        }

        return predicate
    }
}