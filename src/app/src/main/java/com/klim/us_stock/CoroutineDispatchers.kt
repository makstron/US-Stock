package com.klim.us_stock

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class CoroutineDispatchers
@Inject
constructor() {
    open val Main: CoroutineDispatcher = Dispatchers.Main
    open val IO: CoroutineDispatcher = Dispatchers.IO
    open val Default: CoroutineDispatcher = Dispatchers.Default
}