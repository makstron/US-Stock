package com.klim.stock.network.retrofit.typeAdapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonParseException
import com.klim.stock.network.models.HistoryPriceItemResponse
import com.klim.stock.network.models.HistoryPriceResponse
import java.lang.reflect.Type


class HistoryPriceResponseDeserializer : JsonDeserializer<HistoryPriceResponse?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): HistoryPriceResponse {
        val response = json?.asJsonObject?.getAsJsonObject("chart")
        return if (response?.get("error") is JsonNull) {
            response?.getAsJsonArray("result")?.first()?.asJsonObject?.apply {
                val timestamp = getAsJsonArray("timestamp")
                val values = getAsJsonObject("indicators").getAsJsonArray("quote").first().asJsonObject
                val openPrices = values.getAsJsonArray("open")
                val closePrices = values.getAsJsonArray("close")
                val highPrices = values.getAsJsonArray("high")
                val lowPrices = values.getAsJsonArray("low")

                val resultList = mutableListOf<HistoryPriceItemResponse>()
                for (i in 0 until timestamp.size()) {
                    resultList.add(
                        HistoryPriceItemResponse(
                            time = timestamp.get(i).asLong,
                            open = openPrices.get(i).asDouble,
                            close = closePrices.get(i).asDouble,
                            high = highPrices.get(i).asDouble,
                            low = lowPrices.get(i).asDouble,
                        )
                    )
                }
                return HistoryPriceResponse(resultList)
            }
            return HistoryPriceResponse(null, true)
        } else {
            return HistoryPriceResponse(null, true)
        }
    }
}