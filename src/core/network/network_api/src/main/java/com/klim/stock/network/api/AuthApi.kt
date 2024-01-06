package com.klim.stock.network.api

import retrofit2.Call
import retrofit2.http.GET

interface AuthApi {

    @GET("/")
    fun getCookies(): Call<Unit>

    @GET("https://query1.finance.yahoo.com/v1/test/getcrumb")
    fun getCrumb(): Call<String>

}