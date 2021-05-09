package io.proj4.ezgas.utils

import com.github.javafaker.Faker
import io.proj4.ezgas.model.Brand
import io.proj4.ezgas.model.Fuel
import io.proj4.ezgas.model.FuelType.*
import io.proj4.ezgas.model.Place
import io.proj4.ezgas.model.Station
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import java.math.BigDecimal
import java.time.ZoneId
import java.util.concurrent.TimeUnit

private val faker = Faker()

fun fakeBrand() = Brand(
    id = ObjectId().toHexString(),
    name = faker.company().name()
)

fun fakeBrands(count: Int) =
    List(count) { fakeBrand() }

fun fakePlace() = faker.address().run {
    Place(
        houseNumber = buildingNumber(),
        street = streetAddress(),
        neighborhood = secondaryAddress(),
        city = cityName(),
        state = state(),
        postalCode = zipCode(),
        position = GeoJsonPoint(longitude().toDouble(), latitude().toDouble()),
        distance = null
    )
}

fun fakeFuel() = Fuel(
    updatedAt = faker.date()
        .past(15, TimeUnit.DAYS)
        .toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate(),
    price = BigDecimal.valueOf(faker.number().randomNumber(3, true), 2)
)

fun fakeStation() = Station(
    id = ObjectId().toHexString(),
    company = faker.company().name(),
    brand = fakeBrand(),
    place = fakePlace(),
    fuels = mapOf(
        GASOLINE to fakeFuel(),
        ETHANOL to fakeFuel(),
        DIESEL to fakeFuel(),
        DIESEL_S10 to fakeFuel()
    )
)

fun fakeStations(count: Int) =
    List(count) { fakeStation() }
