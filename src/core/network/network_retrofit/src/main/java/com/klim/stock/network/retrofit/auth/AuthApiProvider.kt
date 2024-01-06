package com.klim.stock.network.retrofit.auth

import com.klim.stock.network.api.AuthApi
import com.klim.stock.network.retrofit.interceptors.HeaderInterceptor
import com.klim.stock.network.retrofit.interceptors.LoggingInterceptor
import okhttp3.CookieJar
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthApiProvider(
    val loggingInterceptor: LoggingInterceptor,
    val headerInterceptor: HeaderInterceptor,
    val cookieJar: CookieJar,
    val gsonConverterFactory: GsonConverterFactory,
) {

    private var authApi: AuthApi? = null

    fun getAuthApi(): AuthApi {
        return authApi
            ?: run {
                createApi()
                    .also {
                        authApi = it
                    }
            }
    }

    private fun createApi(): AuthApi {
        return createRetrofit().create(AuthApi::class.java)
    }

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(createOkHttpClient())
            .build()
    }

    private val apiKeyInterceptor: Interceptor
        get() = Interceptor { chain: Interceptor.Chain ->
            var request: Request = chain.request()

            request = request.newBuilder()
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .build()
            chain.proceed(request)
        }

    companion object {
        const val BASE_URL = "https://finance.yahoo.com/"
    }

}