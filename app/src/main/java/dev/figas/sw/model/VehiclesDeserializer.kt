package wiki.kotlin.starwars.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException

import java.lang.reflect.Type

class VehiclesDeserializer : JsonDeserializer<VehiclesArray> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext) =
        VehiclesArray(json.asJsonArray.map { Vehicle(it.asString) })
}
