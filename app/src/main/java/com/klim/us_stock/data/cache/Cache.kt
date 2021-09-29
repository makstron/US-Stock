package com.klim.us_stock.data.cache

interface Cache<K, V> {

    fun put(key: K, value: V?): Boolean

    fun get(key: K): V?

    fun size(): Int

    fun isEmpty(): Boolean

    fun clear()
}