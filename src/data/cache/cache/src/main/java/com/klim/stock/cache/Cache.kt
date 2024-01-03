package com.klim.stock.cache

interface Cache<K, V> {

    operator fun set(key: K, value: V?)

    operator fun get(key: K): V?

    fun size(): Int

    fun isEmpty(): Boolean

    fun clear()
}