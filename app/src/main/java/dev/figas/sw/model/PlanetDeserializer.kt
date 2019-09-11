package wiki.kotlin.starwars.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException

import java.lang.reflect.Type

class PlanetDeserializer : JsonDeserializer<Planet> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Planet {
        if (json.isJsonPrimitive && json.asJsonPrimitive.isString) {
            return Planet(json.asString)
        } else {
            val jsonObject = json.asJsonObject
            val planet = Planet(jsonObject.get(URL).asString)
            planet.name = jsonObject.get(NAME).asString
            return planet
        }

    }

}