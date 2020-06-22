package io.proj4.ezgas.repository

import io.proj4.ezgas.error.PageNotFoundException
import io.proj4.ezgas.model.FuelType
import io.proj4.ezgas.model.SortCriteria.PRICE
import io.proj4.ezgas.model.Station
import io.proj4.ezgas.request.NearbyQuery
import io.proj4.ezgas.request.PageQuery
import io.proj4.ezgas.response.StationWithDistance
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

    override fun findByIdsAndFuels(ids: Set<Int>, fuelTypes: Set<FuelType>): List<Station> {
        val jpql = """
            select distinct s from Station s
            join fetch s.brand
            join fetch s.location.city c
            join fetch c.state
            join fetch s.fuels f
            where s.id in(:ids) and f.key.type in(:fuelTypes)
        """

        return entityManager
                .createQuery(jpql, Station::class.java)
                .setParameter("ids", ids)
                .setParameter("fuelTypes", fuelTypes.toSet())
                .resultList
                .sortedBy { station -> ids.indexOf(station.id) }
    }

    override fun findNearby(nearbyQuery: NearbyQuery, pageQuery: PageQuery): Page<StationWithDistance>? {
        val idsWithDistance = findIdsWithDistance(nearbyQuery)
                .ifEmpty { return null }

        val pageRequest = PageRequest.of(
                pageQuery.pageNumber!!,
                pageQuery.pageSize!!,
                nearbyQuery.sort!!.toSort()
        )

        val ids = idsWithDistance.keys
                .slice(pageRequest)
                .ifEmpty { throw PageNotFoundException(pageRequest.pageNumber) }
                .toSet()

        return findByIdsAndFuels(ids, setOf(nearbyQuery.fuel!!))
                .joinWithDistance(idsWithDistance)
                .let { newPage(it, pageRequest, idsWithDistance.count()) }
    }

    private fun findIdsWithDistance(nearbyQuery: NearbyQuery): Map<Int, Float> = with(nearbyQuery) {
        val sql = """
            select stationId, distance(latitude, longitude, :latitude, :longitude) as distance
            from Station join Fuel using(stationId)
            where fuelType = :fuelType
            having distance <= :distance
            order by ${if (sort == PRICE) "salePrice" else "distance"}
        """

        return entityManager
                .createNativeQuery(sql)
                .setParameter("latitude", latitude)
                .setParameter("longitude", longitude)
                .setParameter("fuelType", fuel!!.name)
                .setParameter("distance", distance)
                .resultList
                .associate {
                    it as Array<*>
                    val stationId = it[0] as Int
                    val distance = it[1] as Float

                    stationId to distance
                }
    }
}