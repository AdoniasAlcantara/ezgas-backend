package io.proj4.ezgas.repository

import io.proj4.ezgas.error.PageNotFoundException
import io.proj4.ezgas.model.FuelType
import io.proj4.ezgas.model.SortCriteria.PRICE
import io.proj4.ezgas.model.Station
import io.proj4.ezgas.repository.mappers.joinWithDistance
import io.proj4.ezgas.request.NearbyQuery
import io.proj4.ezgas.response.StationWithDistanceDto
import io.proj4.ezgas.util.newPage
import io.proj4.ezgas.util.slice
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class StationRepositoryImpl(private val entityManager: EntityManager) : StationRepository {

    override fun findById(id: Int): Station? {
        val jpql = """
            SELECT DISTINCT s FROM Station s
            JOIN FETCH s.brand
            JOIN FETCH s.location.city c
            JOIN FETCH c.state
            JOIN FETCH s.fuels
            WHERE s.id = :id
        """

        return entityManager
                .createQuery(jpql, Station::class.java)
                .setParameter("id", id)
                .runCatching { singleResult }
                .getOrNull()
    }

    override fun findByIdsAndFuelType(ids: Collection<Int>, vararg fuelTypes: FuelType): List<Station> {
        val jpql = """
            SELECT DISTINCT s FROM Station s
            JOIN FETCH s.brand
            JOIN FETCH s.location.city c
            JOIN FETCH c.state
            JOIN FETCH s.fuels f
            WHERE s.id IN(:ids) AND f.key.type IN(:fuelTypes)
            ORDER BY FIND_IN_SET(s.id, :idsStr)
        """

        val fuelTypeSet = if (fuelTypes.any()) {
            fuelTypes.toSet()
        } else {
            FuelType.values().toSet()
        }

        return entityManager
                .createQuery(jpql, Station::class.java)
                .setParameter("ids", ids)
                .setParameter("fuelTypes", fuelTypeSet)
                .setParameter("idsStr", ids.joinToString(","))
                .resultList
    }

    override fun findNearby(nearbyQuery: NearbyQuery): List<StationWithDistanceDto> {
        val idsWithDistance = findIdsWithDistance(nearbyQuery)
                .ifEmpty { return emptyList() }

        return findByIdsAndFuelType(idsWithDistance.keys, nearbyQuery.fuelType)
                .joinWithDistance(idsWithDistance)
    }

    override fun findNearby(nearbyQuery: NearbyQuery, pageNumber: Int, pageSize: Int): Page<StationWithDistanceDto>? {
        val idsWithDistance = findIdsWithDistance(nearbyQuery)
                .ifEmpty { return null }

        val pageable = PageRequest.of(pageNumber, pageSize, nearbyQuery.sortBy.toSort())

        val ids = idsWithDistance.keys
                .slice(pageable)
                .ifEmpty { throw PageNotFoundException(pageable.pageNumber) }

        return findByIdsAndFuelType(ids, nearbyQuery.fuelType)
                .joinWithDistance(idsWithDistance)
                .run { newPage(this, pageable, idsWithDistance.count()) }
    }

    private fun findIdsWithDistance(nearbyQuery: NearbyQuery): Map<Int, Float> = with(nearbyQuery) {
        val sql = """
            SELECT id, DISTANCE(latitude, longitude, :lat, :lng) AS distance
            FROM Station JOIN Fuel ON stationId = id
            WHERE type = :fuelType
            HAVING distance <= :dist
            ORDER BY ${if (sortBy == PRICE) "price" else "distance"} ASC
        """

        return entityManager
                .createNativeQuery(sql)
                .setParameter("lat", latitude)
                .setParameter("lng", longitude)
                .setParameter("fuelType", fuelType.name)
                .setParameter("dist", distance)
                .resultList
                .toList()
                .associate {
                    it as Array<*>
                    it[0] as Int to it[1] as Float
                }
    }
}