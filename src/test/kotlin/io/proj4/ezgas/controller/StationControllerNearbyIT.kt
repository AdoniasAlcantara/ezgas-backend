package io.proj4.ezgas.controller

import io.proj4.ezgas.configuration.RestAssuredTestConfig
import io.proj4.ezgas.model.FuelType
import io.proj4.ezgas.model.SortCriteria
import io.proj4.ezgas.model.Station
import io.proj4.ezgas.utils.distanceTo
import io.proj4.ezgas.utils.fakeStations
import io.proj4.ezgas.utils.resolveIndexesFor
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.ValidatableResponse
import io.restassured.specification.RequestSpecification
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.http.HttpStatus
import kotlin.math.ceil

@Import(RestAssuredTestConfig::class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestInstance(PER_CLASS)
class StationControllerNearbyIT(
    @Autowired baseRequestSpec: RequestSpecification,
    @Autowired private val mongo: MongoOperations
) {
    private val requestSpec = baseRequestSpec.basePath("/stations/nearby")
    private val centre = GeoJsonPoint(-39.375822, -14.675528)
    private val stations = fakeStations(      // Distance from centre
        GeoJsonPoint(-39.374810, -14.674316), //   0.17Km
        GeoJsonPoint(-39.375399, -14.677542), //   0.23Km
        GeoJsonPoint(-39.377953, -14.676223), //   0.24Km
        GeoJsonPoint(-39.376194, -14.672915), //   0.29Km
        GeoJsonPoint(-39.378247, -14.674001), //   0.31Km
        GeoJsonPoint(-39.062959, -14.791306), //  36.02Km
        GeoJsonPoint(-39.047357, -14.798273), //  37.87Km
        GeoJsonPoint(-39.036671, -14.794077), //  38.78Km
        GeoJsonPoint(-39.034629, -14.823109), //  40.19Km
        GeoJsonPoint(-38.455624, -12.938356)  // 217.21Km
    )

    init {
        mongo.dropCollection("stations")
        mongo.resolveIndexesFor<Station>()
        mongo.insert(stations, "stations")
    }

    @Test
    fun `should return stations up to 50Km sorted by distance`() {
        val expectedIds = stations
            .filterByDistance(50.0)
            .map { it.id }

        Given {
            spec(requestSpec)
            param("latitude", centre.y)
            param("longitude", centre.x)
            param("fuel", FuelType.values().random())
            param("distance", 50.0)
            param("sort", SortCriteria.DISTANCE)
        } When {
            get()
        } Then {
            statusCode(HttpStatus.OK.value())
            body("totalPages", `is`(1))
            body("totalElements", `is`(expectedIds.count()))
            body("content.id", equalTo(expectedIds))
        }
    }

    @Test
    fun `should return stations up to 10Km sorted by distance`() {
        val expectedIds = stations
            .filterByDistance(10.0)
            .map { it.id }

        Given {
            spec(requestSpec)
            param("latitude", centre.y)
            param("longitude", centre.x)
            param("fuel", FuelType.values().random())
            param("sort", SortCriteria.DISTANCE)
        } When {
            get()
        } Then {
            statusCode(HttpStatus.OK.value())
            body("totalPages", `is`(1))
            body("totalElements", `is`(expectedIds.count()))
            body("content.id", equalTo(expectedIds))
        }
    }

    @Test
    fun `should return stations up to 10Km sorted by gasoline price`() {
        val expectedIds = stations
            .filterByDistance(10.0)
            .sortedByFuelPrice(FuelType.GASOLINE)
            .map { it.id }

        Given {
            spec(requestSpec)
            param("latitude", centre.y)
            param("longitude", centre.x)
            param("fuel", FuelType.GASOLINE)
            param("sort", SortCriteria.PRICE)
        } When {
            get()
        } Then {
            statusCode(HttpStatus.OK.value())
            body("totalPages", `is`(1))
            body("totalElements", `is`(expectedIds.count()))
            body("content.id", equalTo(expectedIds))
        }
    }

    @Test
    fun `should return stations up to 10Km sorted by ethanol price`() {
        val expectedIds = stations
            .filterByDistance(10.0)
            .sortedByFuelPrice(FuelType.ETHANOL)
            .map { it.id }

        Given {
            spec(requestSpec)
            param("latitude", centre.y)
            param("longitude", centre.x)
            param("fuel", FuelType.ETHANOL)
            param("sort", SortCriteria.PRICE)
        } When {
            get()
        } Then {
            statusCode(HttpStatus.OK.value())
            body("totalPages", `is`(1))
            body("totalElements", `is`(expectedIds.count()))
            body("content.id", equalTo(expectedIds))
        }
    }

    @Test
    fun `should return stations up to 10Km sorted by diesel price`() {
        val expectedIds = stations
            .filterByDistance(10.0)
            .sortedByFuelPrice(FuelType.DIESEL)
            .map { it.id }

        Given {
            spec(requestSpec)
            param("latitude", centre.y)
            param("longitude", centre.x)
            param("fuel", FuelType.DIESEL)
            param("sort", SortCriteria.PRICE)
        } When {
            get()
        } Then {
            statusCode(HttpStatus.OK.value())
            body("totalPages", `is`(1))
            body("totalElements", `is`(expectedIds.count()))
            body("content.id", equalTo(expectedIds))
        }
    }

    @Test
    fun `should return stations up to 10Km sorted by dieselS10 price`() {
        val expectedIds = stations
            .filterByDistance(10.0)
            .sortedByFuelPrice(FuelType.DIESEL_S10)
            .map { it.id }

        Given {
            spec(requestSpec)
            param("latitude", centre.y)
            param("longitude", centre.x)
            param("fuel", FuelType.DIESEL_S10)
            param("sort", SortCriteria.PRICE)
        } When {
            get()
        } Then {
            statusCode(HttpStatus.OK.value())
            body("totalPages", `is`(1))
            body("totalElements", `is`(expectedIds.count()))
            body("content.id", equalTo(expectedIds))
        }
    }

    @Test
    fun `should return the last page of stations up to 50Km sorted by distance`() {
        val distance = 50.0
        val items = stations.filterByDistance(distance)
        val pageSize = 3
        val totalPages = ceil(items.count().toFloat() / pageSize).toInt()
        val lastPage = totalPages - 1
        val expectedIds = items
            .chunked(pageSize)
            .last()
            .map { it.id }

        Given {
            spec(requestSpec)
            param("latitude", centre.y)
            param("longitude", centre.x)
            param("fuel", FuelType.values().random())
            param("distance", distance)
            param("sort", SortCriteria.DISTANCE)
            param("pageNumber", lastPage)
            param("pageSize", pageSize)
        } When {
            get()
        } Then {
            statusCode(HttpStatus.OK.value())
            body("number", `is`(lastPage))
            body("size", `is`(pageSize))
            body("totalElements", `is`(items.count()))
            body("totalPages", `is`(totalPages))
            body("content.id", equalTo(expectedIds))
        }

    }

    @Test
    fun `should return an empty page when there are no stations nearby`() {
        Given {
            spec(requestSpec)
            param("latitude", -43.183683)
            param("longitude", -22.968126)
            param("fuel", FuelType.values().random())
        } When {
            get()
        } Then {
            statusCode(HttpStatus.OK.value())
            body("content.size()", `is`(0))
        }
    }

    @Test
    fun `should return bad request when missing latitude and longitude`() {
        Given {
            spec(requestSpec)
            param("fuel", FuelType.values().random())
        } When {
            get()
        } Then {
            assertThatStatusIsBadRequest()
            body("details.size()", `is`(2))
            body("details.field", hasItem("latitude"))
            body("details.field", hasItem("longitude"))
        }
    }

    @Test
    fun `should return bad request when missing fuel type`() {
        Given {
            spec(requestSpec)
            param("latitude", centre.y)
            param("longitude", centre.x)
        } When {
            get()
        } Then {
            assertThatStatusIsBadRequest()
            body("details.size()", `is`(1))
            body("details[0].field", equalTo("fuel"))
        }
    }

    @Test
    fun `should return bad request when given an invalid fuel type`() {
        Given {
            spec(requestSpec)
            param("latitude", centre.y)
            param("longitude", centre.x)
            param("fuel", "COAL")
        } When {
            get()
        } Then {
            assertThatStatusIsBadRequest()
            body("details.size()", `is`(1))
            body("details[0].field", equalTo("fuel"))
        }
    }

    @Test
    fun `should return bad request when latitude is greater than 90 degrees`() {
        Given {
            spec(requestSpec)
            param("latitude", 90.1)
            param("longitude", centre.x)
            param("fuel", FuelType.values().random())
        } When {
            get()
        } Then {
            assertThatStatusIsBadRequest()
            body("details.size()", `is`(1))
            body("details[0].field", equalTo("latitude"))
        }
    }

    @Test
    fun `should return bad request when latitude is less than -90 degrees`() {
        Given {
            spec(requestSpec)
            param("latitude", -90.1)
            param("longitude", centre.x)
            param("fuel", FuelType.values().random())
        } When {
            get()
        } Then {
            assertThatStatusIsBadRequest()
            body("details.size()", `is`(1))
            body("details[0].field", equalTo("latitude"))
        }
    }

    @Test
    fun `should return bad request when longitude is greater than 180 degrees`() {
        Given {
            spec(requestSpec)
            param("latitude", centre.y)
            param("longitude", 180.1)
            param("fuel", FuelType.values().random())
        } When {
            get()
        } Then {
            assertThatStatusIsBadRequest()
            body("details.size()", `is`(1))
            body("details[0].field", equalTo("longitude"))
        }
    }

    @Test
    fun `should return bad request when longitude is less than -180 degrees`() {
        Given {
            spec(requestSpec)
            param("latitude", centre.y)
            param("longitude", -180.1)
            param("fuel", FuelType.values().random())
        } When {
            get()
        } Then {
            assertThatStatusIsBadRequest()
            body("details.size()", `is`(1))
            body("details[0].field", equalTo("longitude"))
        }
    }

    @Test
    fun `should return bad request when distance is greater than 50Km`() {
        Given {
            spec(requestSpec)
            param("latitude", centre.y)
            param("longitude", centre.x)
            param("fuel", FuelType.values().random())
            param("distance", 50.1)
        } When {
            get()
        } Then {
            assertThatStatusIsBadRequest()
            body("details.size()", `is`(1))
            body("details[0].field", equalTo("distance"))
        }
    }

    @Test
    fun `should return bad request when distance is less than 1Km`() {
        Given {
            spec(requestSpec)
            param("latitude", centre.y)
            param("longitude", centre.x)
            param("fuel", FuelType.values().random())
            param("distance", 0.9)
        } When {
            get()
        } Then {
            assertThatStatusIsBadRequest()
            body("details.size()", `is`(1))
            body("details[0].field", equalTo("distance"))
        }
    }

    @Test
    fun `should return bad request when sort criteria is invalid`() {
        Given {
            spec(requestSpec)
            param("latitude", centre.y)
            param("longitude", centre.x)
            param("fuel", FuelType.values().random())
            param("sort", "RADIUS")
        } When {
            get()
        } Then {
            assertThatStatusIsBadRequest()
            body("details.size()", `is`(1))
            body("details[0].field", equalTo("sort"))
        }
    }

    @Test
    fun `should return bad request when page size is greater than 50`() {
        Given {
            spec(requestSpec)
            param("latitude", centre.y)
            param("longitude", centre.x)
            param("fuel", FuelType.values().random())
            param("pageSize", 51)
        } When {
            get()
        } Then {
            assertThatStatusIsBadRequest()
            body("details.size()", `is`(1))
            body("details[0].field", equalTo("pageSize"))
        }
    }

    private fun ValidatableResponse.assertThatStatusIsBadRequest() {
        statusCode(HttpStatus.BAD_REQUEST.value())
        body("error", equalTo("Bad Request"))
    }

    private fun List<Station>.filterByDistance(distance: Double) = filter {
        centre distanceTo it.place!!.position <= distance
    }

    private fun List<Station>.sortedByFuelPrice(fuelType: FuelType) = sortedBy {
        it.fuels?.get(fuelType)?.price
    }
}
