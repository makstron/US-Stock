package com.klim.stock.network.retrofit.typeAdapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.klim.stock.network.models.details.SymbolRecommendationResponse
import java.lang.reflect.Type


class SymbolRecommendationDeserializer : JsonDeserializer<SymbolRecommendationResponse?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): SymbolRecommendationResponse {
        val response = json?.asJsonObject?.getAsJsonObject("finance")
        val symbols = response?.getAsJsonArray("result")
            ?.first()?.asJsonObject
            ?.getAsJsonArray("recommendedSymbols")

        return if (symbols != null) {
            SymbolRecommendationResponse(
                result = symbols.map {
                    it.asJsonObject.getAsJsonPrimitive("symbol").asString
                },
                error = ""
            )
        } else {
            return SymbolRecommendationResponse(null, "")
        }
    }
}