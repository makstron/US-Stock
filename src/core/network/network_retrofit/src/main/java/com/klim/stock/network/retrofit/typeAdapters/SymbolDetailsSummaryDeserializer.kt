package com.klim.stock.network.retrofit.typeAdapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.klim.stock.network.models.details.SymbolDetailsSummaryResponse
import com.klim.stock.network.models.details.SymbolDetailsSummaryResult
import java.lang.reflect.Type


class SymbolDetailsSummaryDeserializer : JsonDeserializer<SymbolDetailsSummaryResponse?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): SymbolDetailsSummaryResponse {
        if (true) { //TODO: now
            json?.asJsonObject?.
            getAsJsonObject("quoteSummary")?.
            getAsJsonArray("result")?.
            get(0)?.asJsonObject?.
            getAsJsonObject("assetProfile")?.
            apply {
                return SymbolDetailsSummaryResponse(
                    SymbolDetailsSummaryResult(

                        sector = getAsJsonPrimitive("sector").asString, //* : String,
                        industry = getAsJsonPrimitive("industry").asString, //* : String,
                        ceo = "", //* : String, //TODO: now
                        employees = getAsJsonPrimitive("fullTimeEmployees").asInt, //* : Int,
                        hq_address = getAsJsonPrimitive("address1").asString, //* : String,
                        phone = getAsJsonPrimitive("phone").asString, //* : String,
                        description = getAsJsonPrimitive("longBusinessSummary").asString, //* : String,
                        tags = emptyList(), //* : List<String>, //TODO: now
                        similar = emptyList(), //* : List<String>, //TODO: now
                    )
                )
            }
        }
        return SymbolDetailsSummaryResponse(null!!, "") //TODO: now
    }
}