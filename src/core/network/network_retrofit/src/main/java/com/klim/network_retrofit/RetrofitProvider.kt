package com.klim.network_retrofit

import com.google.gson.GsonBuilder
import com.klim.network_api.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    const val BASE_URL = "https://api.polygon.io/"
    const val API_KEY = "pBTRT6y6rRicla11X0dQ4V3lRVg1O9cP"

    val retrofit: Retrofit
        get() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(converter)
            .client(httpClient)
            .build()

    private val converter: GsonConverterFactory
        get() {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            return GsonConverterFactory.create(gson)
        }

    private val httpClient: OkHttpClient
        get() = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .build()

    private val loggingInterceptor: Interceptor
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)//todo modules where take BuildConfig.DEBUG
            return interceptor
        }

    private val apiKeyInterceptor: Interceptor
        get() = Interceptor { chain: Interceptor.Chain ->
            var request = chain.request()
            val url = request.url.newBuilder().addQueryParameter("apiKey", API_KEY).build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }
}