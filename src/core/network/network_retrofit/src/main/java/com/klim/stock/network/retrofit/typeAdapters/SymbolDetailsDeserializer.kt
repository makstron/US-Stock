package com.klim.stock.network.retrofit.typeAdapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.klim.stock.network.models.details.SymbolDetailsResponse
import com.klim.stock.network.models.details.SymbolDetailsResult
import java.lang.reflect.Type


class SymbolDetailsDeserializer : JsonDeserializer<SymbolDetailsResponse?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): SymbolDetailsResponse {
        if (true) { //TODO: now
            json?.asJsonObject?.
            getAsJsonObject("quoteResponse")?.
            getAsJsonArray("result")?.
            get(0)?.asJsonObject?.
            apply {
                return SymbolDetailsResponse(
                    SymbolDetailsResult(
                        symbol = getAsJsonPrimitive("symbol").asString,
                        shortName = getAsJsonPrimitive("shortName").asString,
                        longName = getAsJsonPrimitive("longName").asString,
                        currentPrice = getAsJsonObject("regularMarketPrice").getAsJsonPrimitive("raw").asFloat,
                        marketChange = getAsJsonObject("regularMarketChange").getAsJsonPrimitive("raw").asFloat,
                        marketChangePercent = getAsJsonObject("regularMarketChangePercent").getAsJsonPrimitive("raw").asFloat,
                    )
                )
            }
        }
        return SymbolDetailsResponse(null!!, "") //TODO: now
    }
}