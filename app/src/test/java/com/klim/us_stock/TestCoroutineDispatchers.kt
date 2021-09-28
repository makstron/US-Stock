package com.klim.us_stock

import kotlinx.coroutines.CoroutineDispatcher

class TestCoroutineDispatchers
constructor(
    private val testDispatcher: CoroutineDispatcher,
) : CoroutineDispatchers() {
    override val Main: CoroutineDispatcher = this.testDispatcher
    override val IO: CoroutineDispatcher = this.testDispatcher
    override val Default: CoroutineDispatcher = this.testDispatcher
}