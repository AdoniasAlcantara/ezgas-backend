package io.proj4.ezgas.controller

import io.proj4.ezgas.configuration.RestAssuredTestConfig
import io.proj4.ezgas.model.Station
import io.proj4.ezgas.utils.fakeStations
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.specification.RequestSpecification
import org.assertj.core.api.Assertions.assertThat
import org.bson.types.ObjectId
import org.hamcrest.Matchers
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.http.HttpStatus

@Import(RestAssuredTestConfig::class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestInstance(PER_CLASS)
class StationControllerIT(
    @Autowired baseRequestSpec: RequestSpecification,
    @Autowired private val mongo: MongoOperations
) {
    private val requestSpec = baseRequestSpec.basePath("/stations")
    private val stations = fakeStations(10)

    init {
        mongo.dropCollection("stations")
        mongo.insert(stations, "stations")
    }

    @Nested
    inner class GetById {
        private val expected = stations.first()

        @Test
        fun `should return station when given an existing ID`() {
            Given {
                spec(requestSpec)
            } When {
                get("/{stationId}", expected.id)
            } Then {
                statusCode(HttpStatus.OK.value())
            } Extract {
                val actual = `as`(Station::class.java)
                assertThat(actual).isEqualTo(expected)
            }
        }

        @Test
        fun `should return not found when given a valid but non-existent ID`() {
            val nonExistentId = ObjectId().toHexString()

            Given {
                spec(requestSpec)
            } When {
                get("/{stationId}", nonExistentId)
            } Then {
                statusCode(HttpStatus.NOT_FOUND.value())
                body("status", Matchers.equalTo(404))
                body("error", Matchers.equalTo("Not Found"))
            }
        }

        @Test
        fun `should return not found when given an invalid ID`() {
            val invalidId = "x".repeat(24)

            Given {
                spec(requestSpec)
            } When {
                get("/{stationId}", invalidId)
            } Then {
                statusCode(HttpStatus.NOT_FOUND.value())
                body("status", Matchers.equalTo(404))
                body("error", Matchers.equalTo("Not Found"))
            }
        }
    }

    @Nested
    inner class GetAllById {

        @Test
        fun `should return all stations matching the given IDs`() {
            val expected = stations.shuffled().take(5)
            val sampleIds = expected.joinToString(",") { it.id }

            Given {
                spec(requestSpec)
                param("ids", sampleIds)
            } When {
                get()
            } Then {
                statusCode(HttpStatus.OK.value())
            } Extract {
                val actual = `as`(arrayOf<Station>()::class.java)
                assertThat(actual).containsAll(expected)
                assertThat(actual).hasSameSizeAs(expected)
            }
        }

        @Test
        fun `should return an empty array when no ID matches`() {
            val nonExistentIds = List(5) { ObjectId().toHexString() }

            Given {
                spec(requestSpec)
                param("ids", nonExistentIds)
            } When {
                get()
            } Then {
                statusCode(HttpStatus.OK.value())
            } Extract {
                val actual = `as`(arrayOf<Station>()::class.java)
                assertThat(actual).isEmpty()
            }
        }
    }
}
