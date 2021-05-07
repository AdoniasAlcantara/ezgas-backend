package io.proj4.ezgas.controller

import io.proj4.ezgas.configuration.RestAssuredTestConfig
import io.proj4.ezgas.model.Brand
import io.proj4.ezgas.utils.fakeBrands
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.specification.RequestSpecification
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.http.HttpStatus

@Import(RestAssuredTestConfig::class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class BrandControllerIT(
    @Autowired requestSpecification: RequestSpecification,
    @Autowired private val mongo: MongoOperations,
) {
    private val requestSpec = requestSpecification.basePath("/brands")
    private val expected = fakeBrands(10)

    @BeforeEach
    fun setUp() {
        mongo.dropCollection("brands")
    }

    @Test
    fun `should return all brands successfully`() {
        mongo.insert(expected, "brands")

        Given {
            spec(requestSpec)
        } When {
            get()
        } Then {
            statusCode(HttpStatus.OK.value())
        } Extract {
            val actual = `as`(arrayOf<Brand>()::class.java)
            assertThat(actual).containsAll(expected)
            assertThat(actual).hasSameSizeAs(expected)
        }
    }

    @Test
    fun `should return an empty list`() {
        Given {
            spec(requestSpec)
        } When {
            get()
        } Then {
            statusCode(HttpStatus.OK.value())
        } Extract {
            val actual = `as`(arrayOf<Brand>()::class.java)
            assertThat(actual).isEmpty()
        }
    }
}
