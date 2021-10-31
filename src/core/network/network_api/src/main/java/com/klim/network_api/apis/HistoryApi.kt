package com.klim.network_api.apis

import com.klim.smth.data.retrofit.models.HistoryPriceResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface HistoryApi {

    @GET("v2/aggs/ticker/{symbol}/range/1/day/{olderDate}/{newerDate}?adjusted=true&sort=asc&limit=500000")
    suspend fun getPrice(
        @Path(value = "symbol", encoded = true) symbol: String,
        @Path(value = "olderDate", encoded = true) olderDate: String,
        @Path(value = "newerDate", encoded = true) newerDate: String,
    ): HistoryPriceResponse?
}