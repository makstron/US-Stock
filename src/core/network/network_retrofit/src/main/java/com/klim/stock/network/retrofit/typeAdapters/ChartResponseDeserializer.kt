package com.klim.stock.network.retrofit.typeAdapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonParseException
import com.klim.stock.network.models.ChartItemResponse
import com.klim.stock.network.models.ChartResponse
import java.lang.reflect.Type


class ChartResponseDeserializer : JsonDeserializer<ChartResponse?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ChartResponse {
        val response = json?.asJsonObject?.getAsJsonObject("chart")
        return if (response?.get("error") is JsonNull) {
            response?.getAsJsonArray("result")?.first()?.asJsonObject?.apply {
                val timestamp = getAsJsonArray("timestamp")
                val values = getAsJsonObject("indicators").getAsJsonArray("quote").first().asJsonObject
                val openPrices = values.getAsJsonArray("open")
                val closePrices = values.getAsJsonArray("close")
                val highPrices = values.getAsJsonArray("high")
                val lowPrices = values.getAsJsonArray("low")

                val resultList = mutableListOf<ChartItemResponse>()
                for (i in 0 until timestamp.size()) {
                    resultList.add(
                        ChartItemResponse(
                            time = timestamp.get(i).asLong,
                            open = openPrices.get(i).asDouble,
                            close = closePrices.get(i).asDouble,
                            high = highPrices.get(i).asDouble,
                            low = lowPrices.get(i).asDouble,
                        )
                    )
                }
                return ChartResponse(resultList)
            }
            return ChartResponse(null, true)
        } else {
            return ChartResponse(null, true)
        }
    }
}