package io.proj4.ezgas.utils

import com.github.javafaker.Faker
import io.proj4.ezgas.model.Brand
import org.bson.types.ObjectId

private val faker = Faker()

fun fakeBrands(count: Int) = (0..count).map {
    Brand(
        id = ObjectId().toHexString(),
        name = faker.company().name()
    )
}
