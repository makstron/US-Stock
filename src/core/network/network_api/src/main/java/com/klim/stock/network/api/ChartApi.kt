package com.klim.stock.network.api

import com.klim.stock.network.Constants
import com.klim.stock.network.models.ChartResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ChartApi {

    @GET("v2/aggs/ticker/{symbol}/range/1/day/{olderDate}/{newerDate}?adjusted=true&sort=asc&limit=500000")
    suspend fun getPrice(
        @Path(value = "symbol", encoded = true) symbol: String,
        @Path(value = "olderDate", encoded = true) olderDate: String,
        @Path(value = "newerDate", encoded = true) newerDate: String,
    ): ChartResponse?


    enum class Range(val value: String) {
        ONE_DAY("1d"),
        FIVE_DAYS("5d"),
        ONE_MONTH("1mo"),
        THREE_MONTHS("3mo"),
        SIX_MONTHS("6mo"),
        ONE_YEAR("1y"),
        TWO_YEARS("2y"),
        FIVE_YEARS("5y"),
        TEN_YEARS("10y"),
        YTD("ytd"),
        MAX("max"),
        ;

        override fun toString(): String {
            return value
        }
    }

    enum class TimeInterval(val value: String) {
        ONE_MINUTE("1m"),
        TWO_MINUTES("2m"),
        FIVE_MINUTES("5m"),
        FIFTEEN_MINUTES("15m"),
        THIRTY_MINUTES("30m"),
        SIXTY_MINUTES("60m"),
        NINETY_MINUTES("90m"),
        ONE_HOUR("1h"),
        ONE_DAY("1d"),
        FIVE_DAYS("5d"),
        ONE_WEEK("1wk"),
        ONE_MONTH("1mo"),
        THREE_MONTHS("3mo"),
        ;

        override fun toString(): String {
            return value
        }
    }

    @GET(Constants.SERVER_URL + "v8/finance/chart/{symbol}?region=US&lang=en-US&includePrePost=false&useYfid=true&corsDomain=finance.yahoo.com&.tsrc=finance")
    suspend fun getChart(
        @Path(value = Constants.SUBDOMAIN_KEY) subdomain: String = Constants.SUBDOMAIN_QUERY1,
        @Path(value = "symbol") symbol: String,
        @Query(value = "interval", encoded = true) interval: TimeInterval,
        @Query(value = "range", encoded = true) range: Range,
    ): ChartResponse

}