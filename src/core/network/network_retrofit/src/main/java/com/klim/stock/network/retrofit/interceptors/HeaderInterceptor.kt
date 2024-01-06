package com.klim.stock.network.retrofit.interceptors

import com.klim.stock.storage.api.StorageKeys
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor(
    val storageKeys: StorageKeys,
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()

        request = request.newBuilder()
            .addHeader(USER_AGENT, CURRENT_USER_AGENT)
            .build()
        return chain.proceed(request)
    }

    companion object {
        const val USER_AGENT = "User-Agent"
        const val CURRENT_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"

        const val COOKIE = "Cookie"
    }
}