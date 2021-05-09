package io.proj4.ezgas.configuration

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import io.proj4.ezgas.model.Position
import io.restassured.config.ObjectMapperConfig
import org.springframework.data.mongodb.core.geo.GeoJsonPoint

class GeoJsonPointDeserializer : StdDeserializer<GeoJsonPoint>(Any::class.java) {

    override fun deserialize(
        parser: JsonParser,
        context: DeserializationContext
    ): GeoJsonPoint {
        val (latitude, longitude) = parser.readValueAs(Position::class.java)
        return GeoJsonPoint(longitude, latitude)
    }
}

fun objectMapperConfig(): ObjectMapperConfig {
    val typesModule = SimpleModule().apply {
        addDeserializer(GeoJsonPoint::class.java, GeoJsonPointDeserializer())
    }

    return ObjectMapperConfig().jackson2ObjectMapperFactory { _, _ ->
        ObjectMapper().findAndRegisterModules().also { mapper ->
            mapper.registerModules(typesModule)
        }
    }
}

