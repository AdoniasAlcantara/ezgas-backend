package io.proj4.ezgas.configuration

import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Lazy

@TestConfiguration
class RestAssuredTestConfig {

    @Lazy
    @Bean
    fun createRequestSpecification(
        @Value("\${local.server.port}") port: Int,
        @Value("\${ezgas.api.key}") apiKey: String,
        @Value("\${ezgas.api.secret}") secret: String
    ): RequestSpecification =
        RequestSpecBuilder()
            .setPort(port)
            .addHeader(apiKey, secret)
            .setAccept(ContentType.JSON)
            .build()
}
