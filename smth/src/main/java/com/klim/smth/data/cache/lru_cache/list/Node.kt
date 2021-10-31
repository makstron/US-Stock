package com.klim.smth.data.cache.lru_cache.list

class Node<T>(
    var value: T? = null,
    internal var prev: Node<T>? = null,
    internal var next: Node<T>? = null,
) {
    override fun toString(): String {
//        return "[${prev?.value ?: "null"}<-(${value})->${next?.value ?: "null"}]"
        return "(${value})"
    }
}