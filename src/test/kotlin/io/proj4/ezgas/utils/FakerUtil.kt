package io.proj4.ezgas.utils

import com.github.javafaker.Faker
import io.proj4.ezgas.model.*
import io.proj4.ezgas.model.FuelType.*
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import java.math.BigDecimal
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*
import java.util.concurrent.TimeUnit

private val faker = Faker()

private fun LocalDate.toDate() =
    Date.from(this.atStartOfDay().toInstant(ZoneOffset.MIN))

fun fakeBrand() = Brand(
    id = ObjectId().toHexString(),
    name = faker.company().name()
)

fun fakeBrands(count: Int) =
    List(count) { fakeBrand() }

fun fakePlace(position: GeoJsonPoint) = faker.address().run {
    Place(
        houseNumber = buildingNumber(),
        street = streetAddress(),
        neighborhood = secondaryAddress(),
        city = cityName(),
        state = state(),
        postalCode = zipCode(),
        position = position,
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

fun fakePosition() = faker.address().run {
    GeoJsonPoint(longitude().toDouble(), latitude().toDouble())
}

fun fakeStation(position: GeoJsonPoint = fakePosition()) = Station(
    id = ObjectId().toHexString(),
    company = faker.company().name(),
    brand = fakeBrand(),
    place = fakePlace(position),
    fuels = mapOf(
        GASOLINE to fakeFuel(),
        ETHANOL to fakeFuel(),
        DIESEL to fakeFuel(),
        DIESEL_S10 to fakeFuel()
    )
)

fun fakeStations(count: Int) =
    List(count) { fakeStation() }

fun fakeStations(vararg positions: GeoJsonPoint) =
    positions.map(::fakeStation)

fun fakeFuelHistory(
    stationId: ObjectId,
    fuelType: FuelType,
    startDate: LocalDate = LocalDate.now().minusMonths(1),
    endDate: LocalDate = LocalDate.now(),
    count: Int = 10
): List<FuelHistory> = List(count) {
    FuelHistory(
        id = ObjectId().toHexString(),
        date = faker.date()
            .between(startDate.toDate(), endDate.toDate())
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate(),
        fuelType = fuelType,
        price = BigDecimal.valueOf(faker.number().randomNumber(3, true), 2),
        stationId = stationId
    )
}
