package com.klim.smth.data.cache

interface Cache<K, V> {

    operator fun set(key: K, value: V?)

    operator fun get(key: K): V?

    fun size(): Int

    fun isEmpty(): Boolean

    fun clear()
}