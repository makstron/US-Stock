package com.klim.smth.data.retrofit.apis

import com.klim.smth.data.retrofit.models.SearchResultResponse
import com.klim.smth.data.retrofit.models.SymbolDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchStockSymbolApi {

    @GET("v3/reference/tickers?active=true&sort=ticker&order=asc&limit=100&locale=us")
    suspend fun search(@Query(value = "search", encoded = true) query: String): SearchResultResponse

    @GET("v1/meta/symbols/{ticker}/company")
    suspend fun getDetails(@Path(value = "ticker", encoded = true) ticker: String): SymbolDetailsResponse
}