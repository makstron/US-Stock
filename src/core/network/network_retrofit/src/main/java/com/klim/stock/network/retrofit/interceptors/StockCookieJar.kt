package com.klim.stock.network.retrofit.interceptors

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.klim.stock.storage.api.StorageKeys
import okhttp3.Cookie
import okhttp3.HttpUrl
import java.lang.reflect.Type


class StockCookieJar(
    val storageKeys: StorageKeys,
) : CookieJarI {

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        Log.d("Cookie: ", "Cookies saved") //TODO: now replace logs
        println(cookies.toString())

        Gson().apply {
            storageKeys.setCookies(toJson(cookies))
        }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        var time = System.currentTimeMillis()
        val cookies = Gson().run {
            val listType: Type = object : TypeToken<List<Cookie>?>() {}.type
            fromJson<List<Cookie>>(storageKeys.getCookies(), listType)
        } ?: emptyList()
        time = System.currentTimeMillis() - time
        Log.d("Cookie: ", "LOADING TIME: " + time) //TODO: now replace logs
        return cookies
    }

}