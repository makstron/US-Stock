package com.klim.stock.network

import com.klim.stock.network.api.HistoryApi
import com.klim.stock.network.api.SearchApi
import com.klim.stock.network.api.StockSymbolApi
import com.klim.stock.network.api.SymbolDetailsApi

interface ApiProvider {

    fun getHistoryApi(): HistoryApi

    fun getSearchApi(): SearchApi

    fun getSymbolDetailsApi(): SymbolDetailsApi

    fun getStockSymbolApi(): StockSymbolApi
}