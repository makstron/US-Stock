package com.klim.stock.network.api

import com.klim.stock.network.Constants
import com.klim.stock.network.Constants.SERVER_URL
import com.klim.stock.network.models.search.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchApi {

    @GET("v3/reference/tickers?active=true&sort=ticker&order=asc&limit=100&locale=us")
    suspend fun search(@Query(value = "search", encoded = true) query: String): SearchResponse

    //region yahoo

    @GET(SERVER_URL + "v1/finance/lookup?formatted=true&lang=en-US&type=equity&count=50&start=0&corsDomain=finance.yahoo.com") //&region=US
    suspend fun searchY(
        @Path(value = Constants.SUBDOMAIN_KEY) subdomain: String = Constants.SUBDOMAIN_QUERY2,
        @Query(value = "query", encoded = true) query: String): SearchResponse

}