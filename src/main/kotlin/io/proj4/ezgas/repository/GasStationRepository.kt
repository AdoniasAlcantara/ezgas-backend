package io.proj4.ezgas.repository

import io.proj4.ezgas.model.GasStation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface GasStationRepository : JpaRepository<GasStation, Int>, JpaSpecificationExecutor<GasStation> {

    @Query("""
        SELECT DISTINCT gas FROM GasStation gas
            JOIN FETCH gas.brand
            JOIN FETCH gas.fuels
            JOIN FETCH gas.address.city city
            JOIN FETCH city.state
            WHERE gas.id IN(:ids)
    """)
    fun findByIds(@Param("ids") ids: List<Int>): List<GasStation>

    @Query("CALL findIdsByLocation(:latitude, :longitude, :range)", nativeQuery = true)
    fun findIdsByLocation(
            @Param("latitude") latitude: Double,
            @Param("longitude") longitude: Double,
            @Param("range") range: Float
    ): List<Int>
}