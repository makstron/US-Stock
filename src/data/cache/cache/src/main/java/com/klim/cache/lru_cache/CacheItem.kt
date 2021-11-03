package com.klim.cache.lru_cache

class CacheItem<K, V>(
    val key: K,
    val value: V?,
) {
    override fun toString(): String {
        return "[$key->$value]"
    }
}