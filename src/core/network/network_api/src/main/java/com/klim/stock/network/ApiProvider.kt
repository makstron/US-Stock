package com.klim.stock.network

import com.klim.stock.network.api.HistoryApi
import com.klim.stock.network.api.SearchStockSymbolApi
import com.klim.stock.network.api.StockSymbolApi

interface ApiProvider {

    fun getHistoryApi(): HistoryApi

    fun getSearchStockSymbolApi(): SearchStockSymbolApi

    fun getStockSymbolApi(): StockSymbolApi
}