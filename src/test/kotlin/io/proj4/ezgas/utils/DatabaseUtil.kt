package io.proj4.ezgas.utils

import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver
import org.springframework.data.mongodb.core.indexOps

inline fun <reified T : Any> MongoOperations.resolveIndexesFor() {
    val indexOps = this.indexOps<T>()
    val resolver = MongoPersistentEntityIndexResolver(this.converter.mappingContext)
    resolver.resolveIndexFor(T::class.java).forEach(indexOps::ensureIndex)
}
