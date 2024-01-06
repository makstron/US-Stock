package com.klim.stock.network.retrofit.interceptors

import com.klim.stock.storage.api.StorageKeys
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class CrumbInterceptor(
    val storageKeys: StorageKeys,
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.newBuilder().addQueryParameter(CRUMB, storageKeys.getCrumb()).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }

    companion object {
        const val CRUMB = "crumb"
    }
}