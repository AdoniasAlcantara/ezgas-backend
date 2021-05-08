package io.proj4.ezgas.controller

import io.restassured.builder.RequestSpecBuilder
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = RANDOM_PORT)
class SecurityIT(
    @LocalServerPort port: Int,
    @Value("\${ezgas.api.key}") private val apiKey: String,
    @Value("\${ezgas.api.secret}") private val secret: String
) {
    private val baseRequestSpec = RequestSpecBuilder()
        .setPort(port)
        .setBasePath("/echo")

    @Test
    fun `authorization should be granted when security header is provided`() {
        val requestSpec = baseRequestSpec
            .addHeader(apiKey, secret)
            .build()

        Given {
            spec(requestSpec)
        } When {
            get()
        } Then {
            statusCode(HttpStatus.OK.value())
            body("uptime", Matchers.notNullValue())
        }
    }

    @Test
    fun `should return forbidden when security header is missing`() {
        Given {
            spec(baseRequestSpec.build())
        } When {
            get()
        } Then {
            statusCode(HttpStatus.FORBIDDEN.value())
        }
    }

    @Test
    fun `should return forbidden when security header key is incorrect`() {
        val requestSpec = baseRequestSpec
            .addHeader("foo", secret)
            .build()

        Given {
            spec(requestSpec)
        } When {
            get()
        } Then {
            statusCode(HttpStatus.FORBIDDEN.value())
        }
    }

    @Test
    fun `should return forbidden when security header value is incorrect`() {
        val requestSpec = baseRequestSpec
            .addHeader(apiKey, "bar")
            .build()

        Given {
            spec(requestSpec)
        } When {
            get()
        } Then {
            statusCode(HttpStatus.FORBIDDEN.value())
        }
    }
}
