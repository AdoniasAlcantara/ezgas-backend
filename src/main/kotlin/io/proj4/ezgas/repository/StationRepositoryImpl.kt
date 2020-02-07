package io.proj4.ezgas.repository

import io.proj4.ezgas.model.FuelType
import io.proj4.ezgas.model.SortCriteria.PRICE
import io.proj4.ezgas.model.Station
import io.proj4.ezgas.model.StationIdWithDistance
import io.proj4.ezgas.request.NearbyRequest
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
            JOIN FETCH s.fuels f
            WHERE s.id = :id
        """

        return entityManager
                .createQuery(jpql, Station::class.java)
                .setParameter("id", id)
                .runCatching { singleResult }
                .getOrNull()
    }

    override fun findByIds(ids: Collection<Int>, vararg fuelTypes: FuelType): List<Station> {
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

    override fun findIdsWithDistance(nearbyRequest: NearbyRequest) = with(nearbyRequest) {
        val sql = """
            SELECT id, DISTANCE(latitude, longitude, :lat, :lng) AS distance
            FROM Station JOIN Fuel ON stationId = id
            WHERE type = :fuelType
            HAVING distance <= :dist
            ORDER BY ${if (sortBy == PRICE) "price" else "distance"} ASC
        """

        @Suppress("UNCHECKED_CAST")
        return@with entityManager
                .createNativeQuery(sql, "StationIdWithDistance")
                .setParameter("lat", latitude)
                .setParameter("lng", longitude)
                .setParameter("fuelType", fuelType.name)
                .setParameter("dist", distance)
                .resultList as List<StationIdWithDistance>
    }
}