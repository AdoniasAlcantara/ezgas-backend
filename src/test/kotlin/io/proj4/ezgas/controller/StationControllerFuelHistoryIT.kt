package io.proj4.ezgas.controller

import io.proj4.ezgas.configuration.RestAssuredTestConfig
import io.proj4.ezgas.model.FuelHistory
import io.proj4.ezgas.model.FuelType
import io.proj4.ezgas.utils.fakeFuelHistory
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.specification.RequestSpecification
import org.assertj.core.api.Assertions.assertThat
import org.bson.types.ObjectId
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.http.HttpStatus
import java.time.LocalDate

@Import(RestAssuredTestConfig::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StationControllerFuelHistoryIT(
    @Autowired baseRequestSpec: RequestSpecification,
    @Autowired private val mongo: MongoOperations
) {
    private val requestSpec = baseRequestSpec.basePath("/stations/{id}/fuelHistory")

    @BeforeEach
    fun setUp() {
        mongo.dropCollection("fuelHistory")
    }

    @Test
    fun `should return last week's fuel history if period is omitted`() {
        val stationId = ObjectId()

        val expected = fakeFuelHistory(
            stationId,
            FuelType.GASOLINE,
            LocalDate.now().minusDays(6),
            LocalDate.now()
        )

        val before = fakeFuelHistory(
            stationId,
            FuelType.GASOLINE,
            LocalDate.now().minusWeeks(3),
            LocalDate.now().minusWeeks(2)
        )

        mongo.insert(before + expected, "fuelHistory")

        Given {
            spec(requestSpec)
            pathParam("id", "$stationId")
            param("fuel", FuelType.GASOLINE)
        } When {
            get()
        } Then {
            statusCode(HttpStatus.OK.value())
        } Extract {
            val actual = `as`(arrayOf<FuelHistory>()::class.java)
            assertThat(actual).hasSameSizeAs(expected)
            assertThat(actual).containsAll(expected)
            assertThat(actual).doesNotContainAnyElementsOf(before)
        }
    }

    @Test
    fun `should return the fuel history within a certain period`() {
        val stationId = ObjectId()
        val startDate = LocalDate.of(2021, 12, 1)
        val endDate = LocalDate.of(2021, 12, 31)

        val expected = fakeFuelHistory(
            stationId,
            FuelType.GASOLINE,
            startDate,
            endDate
        )

        val before = fakeFuelHistory(
            stationId,
            FuelType.GASOLINE,
            LocalDate.of(2021, 11, 1),
            LocalDate.of(2021, 11, 30)
        )

        val after = fakeFuelHistory(
            stationId,
            FuelType.GASOLINE,
            LocalDate.of(2022, 1, 1),
            LocalDate.of(2022, 1, 10)
        )

        mongo.insert(before + expected + after, "fuelHistory")

        Given {
            spec(requestSpec)
            pathParam("id", "$stationId")
            param("fuel", FuelType.GASOLINE)
            param("startDate", "$startDate")
            param("endDate", "$endDate")
        } When {
            get()
        } Then {
            statusCode(HttpStatus.OK.value())
        } Extract {
            val actual = `as`(arrayOf<FuelHistory>()::class.java)
            assertThat(actual).hasSameSizeAs(expected)
            assertThat(actual).containsAll(expected)
            assertThat(actual).doesNotContainAnyElementsOf(before)
            assertThat(actual).doesNotContainAnyElementsOf(after)
        }
    }

    @Test
    fun `should return an empty list when period is out of range`() {
        val stationId = ObjectId()
        val sample = fakeFuelHistory(
            stationId,
            FuelType.GASOLINE,
            LocalDate.of(2021, 12, 1),
            LocalDate.of(2021, 12, 31)
        )

        mongo.insert(sample, "fuelHistory")

        Given {
            spec(requestSpec)
            pathParam("id", "$stationId")
            param("fuel", FuelType.GASOLINE)
            param("startDate", "${LocalDate.of(2022, 1, 1)}")
            param("endDate", "${LocalDate.of(2022, 1, 10)}")
        } When {
            get()
        } Then {
            statusCode(HttpStatus.OK.value())
            body("size()", Matchers.`is`(0))
        }
    }

    @Test
    fun `should return bad request when fuel type is missing`() {
        val stationId = ObjectId()
        val sample = fakeFuelHistory(stationId, FuelType.GASOLINE)

        mongo.insert(sample, "fuelHistory")

        Given {
            spec(requestSpec)
            pathParam("id", "$stationId")
        } When {
            get()
        } Then {
            statusCode(HttpStatus.BAD_REQUEST.value())
            body("details.size()", Matchers.`is`(1))
            body("details[0].field", Matchers.equalTo("fuel"))
        }
    }

    @Test
    fun `should return bad request if startDate is later than endDate`() {
        val stationId = ObjectId()
        val sample = fakeFuelHistory(stationId, FuelType.GASOLINE)

        mongo.insert(sample, "fuelHistory")

        Given {
            spec(requestSpec)
            pathParam("id", "$stationId")
            param("startDate", "${LocalDate.now().plusDays(1)}")
            param("endDate", "${LocalDate.now()}")
        } When {
            get()
        } Then {
            statusCode(HttpStatus.BAD_REQUEST.value())
        }
    }
}