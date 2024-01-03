package com.klim.stock.cache.lru_cache.list

class DoublyLinkedList<T> : Iterable<Node<T>> {

    var first: Node<T>? = null
    var last: Node<T>? = null
    var size: Int = 0

    fun addFirst(node: Node<T>) {
        if (first == null) {
            addFirstInList(node)
        } else {
            node.next = first
            first!!.prev = node
            first = node
        }
        size++
    }

    fun add(node: Node<T>): Node<T> {
        if (last == null) {
            addFirstInList(node)
        } else {
            last!!.next = node
            node.prev = last
            last = node
        }
        size++
        return node
    }

    /**
     * Only if list is empty!
     */
    private fun addFirstInList(node: Node<T>) {
        first = node
        last = node
    }

    ////////////////////////////////////////////////////////

    fun removeLast() {
        if (last != null)
            remove(last!!)
    }

    fun remove(node: Node<T>) {
        if (size == 1) {
            first = null
            last = null
        } else {
            val nodePrev = node.prev
            val nodeNext = node.next
            if (nodePrev != null) {
                nodePrev.next = nodeNext
            } else {
                //this is first element
                first = nodeNext
            }
            if (nodeNext != null) {
                nodeNext.prev = nodePrev
            } else {
                //this is last element
                last = nodePrev
            }
            node.prev = null
            node.next = null
        }
        size--
    }

    ////////////////////////////////////////////////////////

    fun clear() {
        first = null
        last = null
    }

    ////////////////////////////////////////////////////////

    override fun iterator(): Iterator<Node<T>> {
        return DoubleIterator(first)
    }

    override fun toString(): String {
        val sb = StringBuilder("DoublyLinkedList[")
        val iterator = this.iterator()
        val hasItems = iterator.hasNext()
        for (element in iterator) {
            sb.append(element)
            sb.append(",")
        }
        if (hasItems)
            sb.setLength(sb.length - 1)
        sb.append("]")
        return sb.toString()
    }

    inner class DoubleIterator(private var cursor: Node<T>?) : Iterator<Node<T>> {

        var tempCursor: Node<T>? = null

        override fun hasNext(): Boolean {
            return cursor != null
        }

        override fun next(): Node<T> {
            tempCursor = cursor
            cursor = cursor!!.next
            return tempCursor!!
        }
    }
}
