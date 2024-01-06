package com.klim.stock.storage.api

interface StorageKeys {

    companion object {
        enum class Key(val key: String, val defValue: String?) {
            COOKIES("cookies", null),
            CRUMB("crumb", null),
            ;
        }
    }

    fun getCookies(): String?
    fun setCookies(value: String?)

    fun getCrumb(): String?
    fun setCrumb(value: String?)

}