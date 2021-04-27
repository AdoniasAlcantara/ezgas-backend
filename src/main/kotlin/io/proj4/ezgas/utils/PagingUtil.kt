package io.proj4.ezgas.utils

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.Aggregation.*
import org.springframework.data.support.PageableExecutionUtils
import kotlin.reflect.KClass

private class CountResult(val value: Long)

inline fun <reified T : Any> MongoOperations.aggregatePage(
    aggregation: Aggregation,
    pageable: Pageable,
    collectionName: String
): Page<T> = aggregatePage(this, aggregation, pageable, collectionName, T::class)

fun <T : Any> aggregatePage(
    mongoOperations: MongoOperations,
    aggregation: Aggregation,
    pageable: Pageable,
    collectionName: String,
    klass: KClass<T>
): Page<T> {
    val operations = aggregation.pipeline.operations.toTypedArray()

    val pagedAggregation = newAggregation(
        *operations,
        sort(pageable.sort),
        skip(pageable.offset),
        limit(pageable.pageSize.toLong())
    )

    val countAggregation = newAggregation(
        *operations,
        group().count().`as`("value"),
    )

    val results = mongoOperations
        .aggregate(pagedAggregation, collectionName, klass.java)
        .mappedResults

    val count = mongoOperations
        .aggregate(countAggregation, collectionName, CountResult::class.java)
        .mappedResults
        .first()
        .value

    return PageableExecutionUtils.getPage(results, pageable) { count }
}
