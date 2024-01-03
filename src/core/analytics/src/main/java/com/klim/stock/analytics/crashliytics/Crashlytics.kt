package com.klim.stock.analytics.crashliytics

interface Crashlytics {

    fun setCustomKey(key: String, value: String)

    fun recordException(throwable: Throwable)

}