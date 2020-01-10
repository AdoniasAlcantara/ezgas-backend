package io.proj4.ezgas.repository

import io.proj4.ezgas.model.Station
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface StationRepository : JpaRepository<Station, Int>, JpaSpecificationExecutor<Station> {

    @Query("""
        SELECT DISTINCT station FROM Station station
            JOIN FETCH station.brand
            JOIN FETCH station.fuels
            JOIN FETCH station.location.city city
            JOIN FETCH city.state
            WHERE station.id IN(:ids)
    """)
    fun findByIds(@Param("ids") ids: List<Int>): List<Station>

    @Query("CALL findIdsByLocation(:latitude, :longitude, :range)", nativeQuery = true)
    fun findIdsByLocation(
            @Param("latitude") latitude: Double,
            @Param("longitude") longitude: Double,
            @Param("range") range: Float
    ): List<Int>
}