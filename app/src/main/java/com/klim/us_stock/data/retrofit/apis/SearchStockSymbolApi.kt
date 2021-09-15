package com.klim.us_stock.data.retrofit.apis

import com.klim.us_stock.data.retrofit.RetrofitProvider.API_KEY
import com.klim.us_stock.data.retrofit.models.SearchResultResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchStockSymbolApi {

    @GET("v3/reference/tickers?active=true&sort=ticker&order=asc&limit=100&locale=us&apiKey=${API_KEY}")
    suspend fun allPosts(@Query(value = "search", encoded = true) query: String): SearchResultResponse

}