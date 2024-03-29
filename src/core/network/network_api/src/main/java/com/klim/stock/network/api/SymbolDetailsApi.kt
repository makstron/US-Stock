package com.klim.stock.network.api

import com.klim.stock.network.Constants
import com.klim.stock.network.models.details.SymbolDetailsResponse
import com.klim.stock.network.models.details.SymbolDetailsSummaryResponse
import com.klim.stock.network.models.details.SymbolRecommendationResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SymbolDetailsApi {

    @GET(Constants.SERVER_URL + "v10/finance/quoteSummary/{symbol}?formatted=true&lang=en-US&modules=assetProfile&corsDomain=finance.yahoo.com")
    suspend fun getDetailsSummary(
        @Path(Constants.SUBDOMAIN_KEY) subdomain: String = Constants.SUBDOMAIN_QUERY1,
        @Path(value = "symbol", encoded = true) symbol: String,
    ): SymbolDetailsSummaryResponse

    @GET(Constants.SERVER_URL + "v7/finance/quote?formatted=true&lang=en-US&region=US&fields=summaryDetail,longName,shortName,headSymbolAsString,regularMarketPrice,regularMarketChange,regularMarketChangePercent,regularMarketVolume&corsDomain=finance.yahoo.com")
    suspend fun getDetails(
        @Path(Constants.SUBDOMAIN_KEY) subdomain: String = Constants.SUBDOMAIN_QUERY2,
        @Query(value = "symbols", encoded = true) symbols: List<String>,
    ): SymbolDetailsResponse

    @GET(Constants.SERVER_URL + "v6/finance/recommendationsbysymbol/{symbol}")
    suspend fun getRecommendations(
        @Path(Constants.SUBDOMAIN_KEY) subdomain: String = Constants.SUBDOMAIN_QUERY2,
        @Path(value = "symbol", encoded = true) symbol: String,
    ): SymbolRecommendationResponse

}