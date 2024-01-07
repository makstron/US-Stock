package com.klim.stock.network.retrofit.typeAdapters

import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonParseException
import com.klim.stock.network.models.details.SymbolDetailsSummaryResponse
import com.klim.stock.network.models.details.SymbolDetailsSummaryResult
import com.klim.stock.network.models.details.SymbolOfficer
import java.lang.reflect.Type


class SymbolDetailsSummaryDeserializer : JsonDeserializer<SymbolDetailsSummaryResponse?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): SymbolDetailsSummaryResponse {
        val response = json?.asJsonObject?.getAsJsonObject("quoteSummary")
        return if (response?.get("error") is JsonNull) {
            response.getAsJsonArray("result")?.get(0)?.asJsonObject?.getAsJsonObject("assetProfile")?.apply {
                return SymbolDetailsSummaryResponse(
                    SymbolDetailsSummaryResult(

                        sector = getAsJsonPrimitive("sector")?.asString ?: "",
                        industry = getAsJsonPrimitive("industry").asString,
                        employees = getAsJsonPrimitive("fullTimeEmployees").asInt,
                        hqAddress = getAsJsonPrimitive("address1").asString,
                        phone = getAsJsonPrimitive("phone").asString,
                        description = getAsJsonPrimitive("longBusinessSummary").asString,
                        officers = mapOfficers(getAsJsonArray("companyOfficers")),
                    )
                )
            }
            return SymbolDetailsSummaryResponse(null, "")
        } else {
            return SymbolDetailsSummaryResponse(null, "")
        }
    }

    private fun mapOfficers(array: JsonArray): List<SymbolOfficer> {
        return array
            .map {
                SymbolOfficer(
                    name = it.asJsonObject.getAsJsonPrimitive("name").asString,
                    title = it.asJsonObject.getAsJsonPrimitive("title").asString
                )
            }
    }
}