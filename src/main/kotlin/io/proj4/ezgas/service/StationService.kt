package io.proj4.ezgas.service

import io.proj4.ezgas.error.InvalidQueryException
import io.proj4.ezgas.error.ResourceNotFoundException
import io.proj4.ezgas.model.FuelHistory
import io.proj4.ezgas.model.SortCriteria.DISTANCE
import io.proj4.ezgas.model.SortCriteria.PRICE
import io.proj4.ezgas.model.Station
import io.proj4.ezgas.repository.FuelHistoryRepository
import io.proj4.ezgas.repository.StationRepository
import io.proj4.ezgas.request.HistoryQuery
import io.proj4.ezgas.request.NearbyQuery
import io.proj4.ezgas.request.Paging
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.temporal.ChronoUnit

@Service
class StationService(
    private val stationRepository: StationRepository,
    private val fuelHistoryRepository: FuelHistoryRepository
) {

    fun findById(id: String): Station =
        stationRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException(id)

    fun findAllById(ids: Set<String>): List<Station> =
        stationRepository.findAllById(ids).toList()

    fun findNearby(query: NearbyQuery, paging: Paging): Page<Station> {
        val sort = when (query.sort!!) {
            DISTANCE -> Sort.by("place.distance")
            PRICE -> Sort.by("fuels.${query.fuel}.price", "place.distance")
        }

        val pageable = PageRequest.of(
            paging.pageNumber!!,
            paging.pageSize!!,
            sort.ascending()
        )

        return stationRepository.findNearby(
            GeoJsonPoint(query.longitude!!, query.latitude!!),
            query.distance!!,
            query.fuel!!,
            pageable
        )
    }

    fun findFuelHistory(id: String, query: HistoryQuery): List<FuelHistory> {
        val (fuelType, startDate, endDate) = query

        if (!ObjectId.isValid(id)) {
            throw InvalidQueryException("$id is not a valid station ID")
        }

        if (endDate!!.isBefore(startDate)) {
            throw InvalidQueryException("startDate $startDate must be before the endDate $endDate")
        }

        if (ChronoUnit.YEARS.between(startDate, endDate) > 1) {
            throw InvalidQueryException("Period between $startDate and $endDate is too long")
        }

        return fuelHistoryRepository.findByStationIdAndFuelTypeAndDateBetween(
            ObjectId(id),
            fuelType!!,
            startDate!!,
            endDate
        )
    }
}
