package io.proj4.ezgas.repository

import io.proj4.ezgas.model.*
import org.springframework.data.jpa.domain.Specification
import java.math.BigDecimal
import javax.persistence.criteria.*

class GasStationSpecification(
        private val gasStationIds: List<Int>,
        private val brandIds: List<Int>,
        private val fuelType: Fuel.Type,
        private val sortCriteria: SortCriteria
) : Specification<GasStation> {

    override fun toPredicate(root: Root<GasStation>, query: CriteriaQuery<*>, builder: CriteriaBuilder): Predicate? {
        query.distinct(true)

        @Suppress("UNCHECKED_CAST")
        val fuelJoin = root.fetch<GasStation, Fuel>("fuels") as Join<GasStation, Fuel>

        root.run {
            fetch<GasStation, Brand>("brand")
            fetch<GasStation, Address>("address")
                    .fetch<Address, City>("city")
                    .fetch<City, State>("state")
        }

        var predicate = builder.and(
                root.get<Int>("id").`in`(gasStationIds),
                builder.equal(fuelJoin.get<Fuel.Type>("type"), fuelType)
        )

        if (brandIds.any()) {
            predicate = builder.and(predicate, root.get<Brand>("brand").`in`(brandIds))
        }

        if (sortCriteria == SortCriteria.PRICE) {
            query.orderBy(builder.asc(fuelJoin.get<BigDecimal>("price")))
        }

        return predicate
    }
}