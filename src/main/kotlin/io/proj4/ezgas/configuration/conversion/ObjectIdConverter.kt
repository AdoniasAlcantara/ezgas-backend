package io.proj4.ezgas.configuration.conversion

import com.fasterxml.jackson.databind.util.StdConverter
import org.bson.types.ObjectId

class ObjectIdConverter : StdConverter<ObjectId, String>() {

    override fun convert(id: ObjectId): String = id.toString()
}