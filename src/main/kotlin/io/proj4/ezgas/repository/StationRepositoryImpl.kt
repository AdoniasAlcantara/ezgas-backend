package io.proj4.ezgas.repository

import io.proj4.ezgas.error.PageNotFoundException
import io.proj4.ezgas.model.FuelType
import io.proj4.ezgas.model.SortCriteria.PRICE
import io.proj4.ezgas.model.Station
import io.proj4.ezgas.request.NearbyQuery
import io.proj4.ezgas.request.PageQuery
import io.proj4.ezgas.response.StationWithDistanceDto
import io.proj4.ezgas.response.mappers.joinWithDistance
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
            select distinct s from Station s
            join fetch s.brand
            join fetch s.location.city c
            join fetch c.state
            join fetch s.fuels
            where s.id = :id
        """

        return entityManager
                .createQuery(jpql, Station::class.java)
                .setParameter("id", id)
                .runCatching { singleResult }
                .getOrNull()
    }

    override fun findByIdsAndFuelType(ids: Collection<Int>, vararg fuelTypes: FuelType): List<Station> {
        val jpql = """
            select distinct s from Station s
            join fetch s.brand
            join fetch s.location.city c
            join fetch c.state
            join fetch s.fuels f
            where s.id in(:ids) and f.key.type in(:fuelTypes)
            order by find_in_set(s.id, :idsStr)
        """

        val fuelTypeSet = if (fuelTypes.any()) fuelTypes.toSet() else FuelType.values().toSet()

        return entityManager
                .createQuery(jpql, Station::class.java)
                .setParameter("ids", ids)
                .setParameter("fuelTypes", fuelTypeSet)
                .setParameter("idsStr", ids.joinToString(","))
                .resultList
    }

    override fun findNearby(nearbyQuery: NearbyQuery, pageQuery: PageQuery): Page<StationWithDistanceDto>? {
        val idsWithDistance = findIdsWithDistance(nearbyQuery)
                .ifEmpty { return null }

        val pageable = PageRequest.of(pageQuery.pageNumber, pageQuery.pageSize, nearbyQuery.sortBy.toSort())

        val ids = idsWithDistance.keys
                .slice(pageable)
                .ifEmpty { throw PageNotFoundException(pageable.pageNumber) }

        return findByIdsAndFuelType(ids, nearbyQuery.fuelType)
                .joinWithDistance(idsWithDistance)
                .run { newPage(this, pageable, idsWithDistance.count()) }
    }

    private fun findIdsWithDistance(nearbyQuery: NearbyQuery): Map<Int, Float> = with(nearbyQuery) {
        val sql = """
            select id, distance(latitude, longitude, :lat, :lng) as distance
            from Station join Fuel on stationId = id
            where type = :fuelType
            having distance <= :dist
            order by ${if (sortBy == PRICE) "price" else "distance"}
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