package com.klim.us_stock.data.retrofit.apis

import com.klim.us_stock.data.retrofit.RetrofitProvider.API_KEY
import com.klim.us_stock.data.retrofit.models.HistoryPriceResponse
import com.klim.us_stock.data.retrofit.models.SymbolPriceResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface HistoryApi {

    @GET("v2/aggs/ticker/{symbol}/range/1/day/{olderDate}/{newerDate}?adjusted=true&sort=asc&limit=500000&apiKey=${API_KEY}")
    suspend fun getPrice(
        @Path(value = "symbol", encoded = true) symbol: String,
        @Path(value = "olderDate", encoded = true) olderDate: String,
        @Path(value = "newerDate", encoded = true) newerDate: String,
    ): HistoryPriceResponse?
}