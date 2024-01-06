package com.klim.stock.network.retrofit.interceptors

import com.klim.stock.network.api.AuthApi
import com.klim.stock.network.retrofit.StockHttpsURLConnection
import com.klim.stock.storage.api.StorageKeys
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(
    val storageKeys: StorageKeys,
    val authApi: AuthApi,
) : Interceptor {
    val TAG = "AuthInterceptor"
    val lock = Any()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response = chain.proceed(request)

        return when (response.code) {
            StockHttpsURLConnection.HTTP_UNAUTHORIZED -> {
                updateCredentials(request)

                return if (!storageKeys.getCookies().isNullOrEmpty() || !storageKeys.getCrumb().isNullOrEmpty()) {
                    response.close()
                    chain.call().clone().execute()
                } else {
                    response
                }
            }

            else -> {
                response
            }
        }
    }

    private fun getCookiesFromRequest(request: Request): String? {
        val cookie = request.header(HeaderInterceptor.COOKIE)
        if (cookie == "null") {
            return null
        }
        return cookie
    }

    private fun getCrumbFromRequest(request: Request): String? {
        val crumb = request.url.queryParameter(CrumbInterceptor.CRUMB)
        if (crumb == "null") {
            return null
        }
        return crumb
    }

    private fun updateCredentials(request: Request) = synchronized(lock) {
        val lastUsedCookies = getCookiesFromRequest(request)
        val lastUsedCrumb = getCrumbFromRequest(request)
        if (
            lastUsedCookies == null || lastUsedCrumb == null ||
            storageKeys.getCookies() == lastUsedCookies || storageKeys.getCrumb() == lastUsedCrumb
        ) {
            getNewCookies()

            val crumb = getNewCrumb()
            storageKeys.setCrumb(crumb)
        }
    }

    private fun getNewCookies() {
        authApi.getCookies().execute() //Will be stored automatically
    }

    private fun getNewCrumb(): String? {
        return authApi.getCrumb().execute().body()
    }

}