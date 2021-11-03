package com.klim.cache.lru_cache

import com.klim.cache.Cache
import com.klim.cache.lru_cache.list.DoublyLinkedList
import com.klim.cache.lru_cache.list.Node

class LRUCache<K, V>(
    val maxSize: Int = 0
) : Cache<K, V> {

    private val list = DoublyLinkedList<CacheItem<K, V>>()
    private val map = HashMap<K, Node<CacheItem<K, V>>>()

    override fun set(key: K, value: V?) {
        val cacheItem = CacheItem(key, value)
        val node = Node(cacheItem)
        val existingNode = map[key]

        if (existingNode != null) {
            list.remove(existingNode)
        } else {
            if (size() >= maxSize) {
                if (list.last?.value != null) {
                    map.remove(list.last!!.value!!.key)
                    list.removeLast()
                }
            }
        }
        list.addFirst(node)
        map[key] = node
    }

    override fun get(key: K): V? {
        val existingNode = map[key]
        if (existingNode != null) {
            list.remove(existingNode)
            list.addFirst(existingNode)
        }
        return existingNode?.value?.value
    }

    override fun size() = list.size

    override fun isEmpty(): Boolean {
        return size() == 0
    }

    override fun clear() {
        map.clear()
        list.clear()
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("LRUCache(${size()})[elements=")

        sb.append(list)
        sb.append("]")
        return sb.toString()
    }
}