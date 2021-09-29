package com.klim.us_stock.data.retrofit.apis

import com.klim.us_stock.data.retrofit.RetrofitProvider.API_KEY
import com.klim.us_stock.data.retrofit.models.SymbolPriceResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface StockSymbolApi {

    @GET("v1/open-close/{symbol}/{date}?adjusted=true")
    suspend fun getPrice(
        @Path(value = "symbol", encoded = true) symbol: String,
        @Path(value = "date", encoded = true) date: String,
    ): SymbolPriceResponse
}